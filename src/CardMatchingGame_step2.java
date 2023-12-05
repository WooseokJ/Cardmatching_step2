
import java.util.*;

public class CardMatchingGame_step2 {
    private static final int row = 3;
    private static final int col = 6;
    private List<Integer> cardList;
    private String[][] xmarkList = new String[row][col];
    private int[][] board;
    private boolean[][] isShow;
    private boolean tmpShow[][]; // 잠시 보여주는 용도.
    private static CardMatchingGame_step2 T ;
    private int currentPlayerIndex = 0;
    private Player[] players = new Player[2];

    // 객체 선언시 초기화 과정.
    private CardMatchingGame_step2() {
        initializePlayers();
        initializeDeck();
        setupBoard();
    }

    // 24 개 1~8 3묶음 뽑고 섞기.
    private void initializeDeck() {
        cardList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 0; j < 3; j++) {
                cardList.add(i);
            }
        }
        Collections.shuffle(cardList);
    }

    // 24개중 18개 카드 넣기
    private void setupBoard() {
        board = new int[row][col];
        isShow = new boolean[row][col];
        tmpShow = new boolean[row][col];

        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col ; j++) {
                if(count < 18 ) {
                    board[i][j] = cardList.get(count++);
                    xmarkList[i][j] = "X";
                } else {
                    board[i][j] = -1;
                }
            }
        }
    }

    // 콘솔에 출력.
    private void showBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (tmpShow[i][j]) {
                    System.out.printf(board[i][j] + " ");
                    tmpShow[i][j] = false;
                    continue;
                }
                if (isShow[i][j]) {
                    System.out.printf(board[i][j] + " ");
                    isShow[i][j] = false;
                    xmarkList[i][j] = " ";
                    board[i][j] = -1; // 찾은거는 -1로 변환.
                } else {
                    System.out.printf(xmarkList[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // 입력받는 좌표 반환.(참고: 입력이 이상한경우에대한 예외처리)
    private Coordinates inputCoordinator(Scanner scanner, String text, int rowInput, int colInput) throws Exception {
        while(true) {
            try {
                System.out.printf("%s", text);
                String input1 = scanner.nextLine();
                input1 = input1.replaceAll("[()]", "");
                String[] parts = input1.split(",");

                if (parts.length != 2) { // 행과 열의 입력값 구조가 아닐떄
                    throw new CustomException(CustomException.ErrorType.inputMatrixMessage);
                } else if (isInteger(parts[0]) || isInteger(parts[1]) ) { // 행 과 열이 정수가 아닐떄
                    throw new CustomException(CustomException.ErrorType.inputMatrixMessage);
                }
                int row = Integer.parseInt(parts[0].trim()) - 1;
                int col = Integer.parseInt(parts[1].trim()) - 1;

                if(row == rowInput && col == colInput) { // 이전좌표와 입력한 좌표가 같을떄
                    throw new CustomException(CustomException.ErrorType.differentCoordinateMessage);
                }
                if(row < 0 || col < 0 || row > 3 || col > 6) { // 범위를 벗어날떄
                    throw new CustomException(CustomException.ErrorType.outOfRangeMessage);
                }
                if (xmarkList[row][col] == " ") { // // 이미 찾은 카드일떄
                    throw new CustomException(CustomException.ErrorType.alreadyFoundMessage);
                }
                return new Coordinates(row, col);
            } catch (CustomException e) {
                if(e.getErrorType() == CustomException.ErrorType.inputMatrixMessage) {
                    System.out.println("행과 열을 입력해주세요. ex) (1,1) 혹은 1,1");
                } else if (e.getErrorType() == CustomException.ErrorType.outOfRangeMessage){
                    System.out.println("범위가 벗어났습니다.");
                } else if (e.getErrorType() == CustomException.ErrorType.alreadyFoundMessage) {
                    System.out.println("해당 자리는 이미 찾았습니다. ");
                } else if (e.getErrorType() == CustomException.ErrorType.differentCoordinateMessage){
                    System.out.println("이전좌표랑 다른좌표를 입력해주세요.");
                } else {
                    System.out.println("알수없는 오류");
                }
                continue;
            }

        }
    }
    // 해당 값이 정수인지 아닌지 판단.
    public static boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }

    // 게임 시작
    private void playGame() throws Exception {
        int matches = 0; // 매칭된 카드 짝
        Scanner scanner = new Scanner(System.in);
        System.out.println(players[0].getName() + "의 차례입니다.");
        while (matches < 9) {
            int num = 0;
            Player currentPlayer = players[currentPlayerIndex];
            showBoard();
            System.out.println("<시도 " + (currentPlayer.getAttempts() + 1) + ", 남은카드: " + (18 - matches * 2) + "> 좌표를 두 번 입력하세요.");

            Coordinates matrix1 = new Coordinates();
            matrix1 = inputCoordinator(scanner, "입력 1? ", matrix1.getRow(), matrix1.getCol());
            int row1 = matrix1.getRow(), col1 = matrix1.getCol();

            Coordinates matrix2 = new Coordinates();
            matrix2 = inputCoordinator(scanner, "입력 2? ", row1, col1);
            int row2 = matrix2.getRow(), col2 = matrix2.getCol();

            if (board[row1][col1] == board[row2][col2]) {
                isShow[row1][col1] = true;
                isShow[row2][col2] = true;
                currentPlayer.sumScore();
                matches++;
            } else {
                tmpShow[row1][col1] = true;
                tmpShow[row2][col2] = true;
                changeTurn();
            }
            currentPlayer.onePlusAttempts();

            if(checkCard(board)) { // 뒤집을수있는 카드를 모두다 뒤집으면 게임종료.
                gameOver(scanner);
                break;
            }
        }
    }
    // 모든카드가 다 뒤집어지면 게임종료와 함께 정보 출력.
    private void gameOver(Scanner scanner) {
        System.out.println("==========================");

        System.out.println(players[0].getName()+"의 최종점수: " + players[0].getScore());
        System.out.println(players[1].getName() + "의 최종점수: " + players[1].getScore());

        if(players[0].getScore() > players[1].getScore()) {
            System.out.println("승리: " + players[0].getName() + " 🎉" );
        } else if (players[0].getScore() == players[1].getScore()) {
            System.out.println("무승부입니다." + " 🥲");
        }else {
            System.out.println("승리: " + players[1].getName() + " 🎉");
        }

        scanner.close();
    }
    // 상대턴으로 턴을 넘김.
    private void changeTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        System.out.println(players[currentPlayerIndex].getName() + "의 차례입니다.");
    }
    // 현재 카드의 숫자들의 원소개수 카운팅한뒤에 뒤집을 카드가 남아있는지 아닌지 return
    private Boolean checkCard(int[][] array) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num[] : array) {
            for(int n : num) {
                countMap.put(n, countMap.getOrDefault(n, 0) + 1);
            }
        }
        return areAllValuesCheck(countMap);
    }
    //  현재 뒤집을수있는 카드가 남아있는지 아닌지 판단.
    private boolean areAllValuesCheck(Map<Integer, Integer> map) {
        for (int value : map.values()) {
            if (value == 2) {
                return false; // 짝이 남아있으면 false
            }
        }
        return true; // 짝이 남아있지않으면 true
    }
    // 플레이어 이름 입력받는 메서드.
    private void initializePlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("player 1 이름을 작성해주세요: ");
        String player1Name = scanner.nextLine();
        players[0] = new Player(player1Name);

        System.out.print("player 2 이름을 작성해주세요: ");
        String player2Name = scanner.nextLine();
        players[1] = new Player(player2Name);
    }

    public static void main(String[] args) {
        try {
            T = new CardMatchingGame_step2();
            T.playGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
