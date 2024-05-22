# week7
멋쟁이사자처럼 12기 서버 7주차 과제

비즈니스 로직 목록
1. 회원가입할 수 있다.
2. 로그아웃 할 수 있다.
3. 로그인 할 수 있다.
4. 메시지를 전송할 수 있다.
5. 메시지를 단체 전송할 수 있다.
6. 메시지를 보관함에 저장할 수 있다.
7. 보관함에 있는 메시지 목록을 볼 수 있다.
8. 답장을 보낼 수 있다.
9. 어떤 메시지에 대한 답장인지 볼 수 있다.
10. 아무도 읽지 않은 메시지만 수정 및 삭제할 수 있다.

API 설계서
|CRUD|HTTP|URI|
|----|----|---|
|회원가입 하기|Post|/auth/sign-in|
|로그인 하기|Get|/auth/login|
|로그아웃 하기|Get|/auth/logout|
|직원 정보 수정하기|Patch|/employees|
|내 정보 및 메시지함 조회하기|Get|/employees|
|회원 탈퇴하기|Delete|/employees|
|메시지 전송하기|Post|/messages|
|답장 전송하기|Post|/messages/reply/{msgId}|
|메시지 아이디로 메시지 조회하기|Get|/messages/{msgId}|
|메시지 수정하기|Patch|/messages/{msgId}|
|메시지 삭제하기|Delete|/messages/{msgId}|
