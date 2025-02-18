# java-attendance

출석 미션 저장소

2024년 12월

# java-attendance

출석 미션 저장소


## 기능 요구 사항


2024년 12월 출석 시스템을 개발

출석 관리 규칙 및 시스템 설계 정책

시간은 24시간 형식만 사용한다.
교육 시간은 월요일은 13:00~18:00, 화요일~금요일은 10:00~18:00이다.
해당 요일의 시작 시각으로부터 5분 초과는 지각으로 간주한다.
해당 요일의 시작 시각으로부터 30분 초과는 결석으로 간주한다.
등교하지 않아 출석 기록이 없는 날은 결석으로 간주한다.
누적 지각 및 결석 횟수에 따라 경고 또는 면담을 시행한다. 또한 결석 횟수가 5회를 초과할 때 제적을 시행한다.
지각 3회는 결석 1회로 간주한다.
경고 대상자: 결석 2회 이상
면담 대상자: 결석 3회 이상
제적 대상자: 결석 5회 초과
캠퍼스 운영 시간은 매일 08:00~23:00이다.
주말 및 공휴일에는 출석을 받지 않는다.
출석 시스템에 등록된 크루와 12월 출석 기록은 제공된 파일(attendances.csv)에서 확인할 수 있다.
프로그램은 사용자가 종료할 때까지 종료되지 않으며, 해당 기능을 수행한 후 초기 화면으로 돌아간다.

## 데이터 초기화
- [] 출결 파일을 읽어와 데이터를 파싱하여 저장한다.

## 기능 선택
### 입력
- [] 원하는 기능을 입력받는다.
### 출력
- [] 오늘 날짜를 출력한다.
### 기능구현
- [] 기능을 선택한다.
### 예외처리
- [] 기능 번호 이외의 명령을 입력할 경우

## 출석 확인

### 입력
- [] 닉네임을 입력받는다.
- [] 시간을 입력받는다.
### 출력
- [] 출석한 시간에 따라 결과를 출력한다.
### 기능 구현
- [] 이미 출석 기록이 있는 경우, 기능 선택화면으로 돌아간다.
- [] 출석한 시간에 맞는 출결 결과를 구한다. ex) 결석, 지각, 출석
### 예외처리
- [] 등록되지 않는 닉네을 입력시
- [] 시간입력 형식을 지키지 않을 경우
- [] 시간이 캠퍼스 운영시간 전이거나 후인경우
- [] 시간 입력은 다음과 같은 형식을 지켜야한다. ex) 23:34
- [] 캠퍼스 운영일이 아닌 경우 (주말, 공휴일)


## 출석 수정

### 입력
- [] 닉네임을 입력 받는다.
- [] 수정하려는 날짜를 입력 받는다.
- [] 수정하려는 시간을 입력 받는다.
### 출력
- [] 기존 시간과 변경 시간을 출력한다.
### 기능 구현
- [] 출석한 시간에 맞는 출결 결과를 구한다. ex) 결석, 지각, 출석
- [] 기존의 출석기록 여부를 확인한다.
- [] 출석 시간을 수정한다.
### 예외처리
- [] 등록되지 않는 닉네임을 입력시
- [] 시간입력 형식을 지키지 않을 경우
- [] 기존의 출석 기록이 없는 경우
- [] 날짜 형식을 지켜야한다. (1~31)

## 크루별 출석 기록 확인
### 입력
- [] 닉네임 입력받는다.
### 출력
- [] 해당 닉네임의 출석 기록을 출력한다.
  - [] 결석일자의 경우 다음과 같은 시간 형식을 지킨다 --:--
- [] 출석 기록별 횟수를 출력한다.
- [] 면담 대상자 여부를 출력한다.
### 기능 구현
- [] 해당 닉네임의 출석 기록을 가져온다. (오름차순으로 정렬)
  - [] 오늘 날짜까지의 출석기록을 가져온다.
- [] 출석한 시간에 맞는 출결 결과를 구한다. ex) 결석, 지각, 출석
- [] 결석 일자를 판단한다.
  - [] 운영 일자를 판단한다.
  - [] 해당 닉네임에 해당 일자 기록여부를 확인한다.
- [] 출석 기록별 횟수를 구한다.
- [] 면담 대상자 여부를 구한다.
### 예외처리
- [] 등록되지 않는 닉네임을 입력시

## 제적 위험자 확인
### 입력
### 출력
- [] 사용자별 출석 기록별 횟수를 출력한다.
- [] 제적,면담,경고 대상자순으로 출력한다.
### 기능 구현
- [] 사용자별 출석 기록을 조회한다.
- [] 제적,면담,경고 대상 여부를 확인한다.
- [] 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.
### 예외 처리
- [] 출석 기록이 하나도 없는 경우
전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며, 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.
제적 위험자 조회 결과
- 빙티: 결석 3회, 지각 4회 (면담)
- 이든: 결석 2회, 지각 5회 (면담)
- 빙봉: 결석 1회, 지각 6회 (면담)
- 쿠키: 결석 2회, 지각 3회 (면담)
- 짱수: 결석 0회, 지각 6회 (경고)

## 프로그래밍 요구 사항
   자바 코드 컨벤션을 지키면서 프로그래밍한다.
   기본적으로 Java Style Guide을 원칙으로 한다.
   indent(인덴트, 들여쓰기) depth를 2를 넘지 않도록 구현한다. 1까지만 허용한다.
   예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
   힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메서드)를 분리하면 된다.
   3항 연산자를 쓰지 않는다.
   else 예약어를 쓰지 않는다.
   else 예약어를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
   힌트: if문에서 값을 반환하는 방식으로 구현하면 else 예약어를 사용하지 않아도 된다.
   추가된 요구 사항
   모든 기능을 TDD로 구현해 단위 테스트가 존재해야 한다. 단, UI(System.out, System.in) 로직은 제외
   핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
   UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.
   함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
   함수(또는 메서드)가 한 가지 일만 하도록 최대한 작게 만들어라.
   배열 대신 컬렉션을 사용한다.
   Java Enum을 적용한다.
   모든 원시 값과 문자열을 포장한다
   줄여 쓰지 않는다(축약 금지).
   일급 컬렉션을 쓴다.