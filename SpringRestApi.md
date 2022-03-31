
# API
- Application Programming Interface

# REST
- Representational State Transfer
- 인터넷 상의 시스템 간의 상호 운용성 을 제공하는 방법중 하나
- 시스템 게각각의 독립적인 진화를 보장하기 위한 방법

# Rest 아키텍쳐 스타일
- client-Server
- stateless
- Cache
- Uniform interface
    - identification of resources
    - manipulation of resources through representation
    - self-descrive message
  > 메세지 자체가 변하더라도 클라이언트는 언제든지 클라이언트는 바뀐메세지에 대응을 할 수 있어야한다.
    - hypermisa as the engine of application state(HATEOAS)
  > 하이퍼링크를 통해 애플리케이션 상태 변화가 가능해야 한다. 링크 정보를 동적으로 바꿀 수 있다. 어떤 응답에 대해 다음 에플리케이션에 다음 상태로 전의를
  > 할 수 있는 정보다 담긴 url 을 던져주고 있는가.
  
- Layered System
- Code-On-Demand(optional)

