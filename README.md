## 구현과정
step1) 초기화 과정에서 각 플레이어 이름 입력받는 initializePlayer를 호출. 
이떄 Player라는 클래스를 만들어 배열로 선언하여  Player객체를 넣을떄 입력받은 값을 초기화하는 과정에서 name의 값을 넣어준다.

step2) 1~8의 배열 3묶음을 하나의 ArrayList에 담은뒤 Collecions클래스의 shuffle을 통해 섞었습니다.

step3) 하나의 ArrayList들중 18를 뽑아 board라는 배열에 담는 작업을 하였고 이를 출력합니다.
이떄 제가 고민했던 부분은 24개의 카드중 18개의 원소를 뽑을떄 18개의 원소가 서로 짝을 이루게 뽑는방법에대해 고민을 많이하였고 18개를 뽑으면 짝이 안맞는 것들이 남아 게임이 계속 재실행하는 상황이 생기는게 의아했습니다. 

step4) 입력값이 유효한지 여부를 판단하여 두개의 좌표값을통해 board를 비교하여 카드를 보여주었습니다.
이떄 제가 신경썻던 부분은 입력값에서 해당 입력값이 유효한지아닌지에대해 에러처리입니다. 처음엔 정규식을 써서 해결할까 하다가 java문법 에러처리문과 enum을 통해 CustomError를 활용해보고싶어서 선택하게되었습니다

step5) 만약 카드가 불일치하면 차례를 넘기는 chageTurn 메서드 를 호출하고 카드가 일치하면 currentPlayer의 score를 더한다. 그리고 한짝을 맞췄으므로 
matches 값을 +1 카운팅 해준다. 
여기서는 chageTurn메서드는 Player 배열의 인덱스를 통해  currentPlayer를 바꾸고 점수계산이나 값을 가져오는것은 Player 클래스안에 sumScore 메서드 나 getter-setter를 미리 구현해두었다. 

step5) 해당 입력이끝나면 남아있는 카드가 원소가 아직 짝을 이루는것이 남아있는지 여부를 판단하였습니다.
이떄 제가 신경썻던 부분은 여부는 판단여부는 HashMap을 활용하여 각 원소에대한 개수를 카운트하고 각 숫자별로 원소개수는 최대 3장이므로 2개의 짝이 남아있는지 여부를 이용해 판단하였습니다.

step6) 게임종료 메세지 콘솔출력 


## 메서드 정리
 * CardMatchingGame: 게임 초기화
 * initalizeDeck: 1~8 3묶음으로 카드 넣고 셔플
 * setupBoard: 24개중 18개뽑아 넣기.
 * showBoard: board 출력
 * inputCoordinator : 좌표값 입력받기
 * isInteger : 정수인지 아닌지 판단
 * playGame : 게임 스타트
 * gameOver : 게임종료되면 실행
 * changeTurn : 상대턴으로 턴을 넘김.
 * checkCard : 각 카드의 원소가 몇개씩있는지 판단.
 * areAllValuesCheck :카드 짝이 맞는지 확인
 * initializePlayers: 플레이어 이름 작성

