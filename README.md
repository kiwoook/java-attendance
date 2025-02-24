# java-attendance

출석 미션 저장소

## 기능 사항 정리

- 출석 확인은 출석 데이터가 저장된 시간(지금)을 기준으로 한다.
- 24시간 형식만 사용할 수 있다.
- 월요일은 1시, (화~금)은 10시에 교육 시작을 한다.
  - 교육 시간 5분 초과는 지각
  - 30분 초과는 결석으로 간주한다.
- 누적 지각 및 결석 횟수에 따라 경고 면담을 진행한다.
  - 지각 3회는 결석 1회로 간주한다
  - 경고 대상자는 1회 초과
  - 면담 대상자는 2회 초과
  - 제적 대상자는 5회 초과이다
- 캠퍼스 운영시간은 매일 8~23시까지이다.
  - 이 외에 대한 입력은 에러를 반환한다.
- 주말과 공휴일에 대해서는 출석을 받지 않는다.
- 12월 출석기록은 csv로 제공된다.
  - 해당 파일에는 잘못된 데이터가 없다고 간주한다.
- 프로그램은 사용자가 종료할 때까지 종료되지 않는다.
  - 사용자는 잘못된 입력을 했을 경우 안내를 하고 초기입력 화면으로 돌아간다.
- 실행 날짜를 오늘(today)로 간주하며 전날은 오늘에 대한 전날이다.

### 파일 저장

- 문자열들을 파싱하여 저장한다
- 이름과  (날짜,시간)이 합쳐진 값으로 되어 있다.

### 출석 확인

- 닉네임을 입력한다.
  - 기록에 존재하지 않는 닉네임이라면 에러를 반환한다.
- 등교시간을 입력한다.
  - 출석 기록이 이미 존재한다면 에러를 반환한다.

### 출석 수정

- 닉네임을 입력한다.
  - 존재하지 않으면 에러 반환
- 수정 날짜 입력
  - 수정하려는 날짜가 없다면 에러 반환
- 시간 변경을 한다.
- 변경 전의 시간과 변경 후의 시간을 사용자에게 알려준다.
- 에러가 발생된다면 초기 화면으로 돌아간다.

### 출석 기록 확인

- 닉네임을 입력한다
  - 존재하지 않으면 에러 반환
- 출석 기록을 반환한다.
  - 시작일부터 전날까지의 출석 기록을 반환한다.
  - 데이터가 없는 내용은 결석으로 간주하여 처리한다.
  - 출석, 지각, 결석 횟수를 반환한다. (3,0,3)
  - 경고 대상자 여부인지 출력한다.

### 제적 위험자 확인

- 출석 기록을 바탕으로 제적 위험자를 파악한다.
- 문제가  없는 크루원은 제외한다.
- 다음과 같은 순서대로 정렬되어 출력된다.
  - 제적, 면담, 경고 대상자 순
- 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차 순
- 이 위에 똑같을 시 닉네임으로 오름차순으로 한다.
- 제적 위험자 조회를 할 때는 가지고 있는 모든 데이터를 기준으로 정렬된다.

## 도메인 설계

### Attendance

- localDate와 LocalTime을 가지고 있다.
- 운영 시간이 아니라면 에러를 발생한다.
- 출석 날짜가 있는 지 알 수 있다.
- 출석 시간을 수정할 수 있다.

### Crew

- 이름과 출석 정보들을 저장한다.
- 출석 정보는 오름차순되어 반환된다.
- 해당 출석 날짜가 있는 지 알 수 있으며 출석 정보를 수정할 수 있다.
- 출석을 추가할 수 있다.
- 오늘 날짜를 기준으로 시작일에서 전날까지의 출석 현황을 알 수 있다.
- 같은 출석이 존재하면 에러를 반환한다.
- 출석 날짜에 출석 시간을 받아온다.
  - 해당 출석 날짜가 없으면 에러를 반환한다.

### AttendanceStats

- 크루의 출결 상태를 관리한다.
- 12월 시작 날짜부터 전날까지의 AttendanceStatus의 모음이다.
- 해당 값을 통하여 경고, 면담, 제적을 알 수 있다.
- 크루의 정렬을 위해 지각을 결석으로 간주한 값을 반환한다.
- 패널티 상태를 반환한다.

### PenaltyStatus

- 크루의 출결 상태를 통하여 경고, 면담, 제적 여부를 알 수 있다.
- 지각 3회는 결석 1회로 간주하여 다음과 같이 계산한다.
- 경고 대상자는 결석 2회 이상
- 면담 대상자는 결석 3회 이상
- 제적 대상자는 결석 6회 이상

### Crews

- 모든 크루들의 정보를 관리한다.
- 크루가 존재하는지 확인한다.
- 크루의 출석을 수정한다.
- 크루의 제적 위험자를 확인할 수 있다.
  - 제적 위험자는 제적, 면담, 경고 대상자 순
  - 지각을 결석으로 간주하여 내림차 순
  - 출석 상태가 같으면 닉네임을 오름차순으로 한다.

## 상수와 유틸리티

### WorkDayChecker

- 날짜를 받고 운영시간인지 아닌지 처리한다.

### AttendanceStatus

- 날짜와 시간을 통해 출결 상태가 어떤 지 반환한다.
- 만약 날짜가 운영되는 날짜가 아니라면 에러를 반환한다.
  - 주말, 공휴일을 말한다.
- 평일 요일에 따라서 출석 상태 기준은 달라진다.
  - 월요일 1시 나머지 10시
- 교육 시간 5분 초과는 지각
- 30분 초과는 결석으로 간주한다.
