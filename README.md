# SMASHING-ANDROID

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/b2c823be-d58b-4770-87a5-25ad4b77da68" />

# 스매싱

**스매싱 - 스포츠인을 위한 매칭은 계속된다**


<img width="100%" alt="Image" src="https://github.com/user-attachments/assets/6cbeb050-50df-4f5c-9084-721ab8689100" />

> **스매싱**은 **2030 세대의 스포츠 자아 완성을 돕는 게이미키케이션 기반** 스포츠 매칭 플랫폼 입니다.

<br/>

## 🏸주요 기능

<img width="590" height="831" alt="스크린샷, 2026-01-23 오후 8 12 03" src="https://github.com/user-attachments/assets/56a29b3f-c67e-4be3-8323-ee43b8a74582" />

## **✨ Contributors**

| 🏸공승준<br/>[@seungjunGong](https://github.com/seungjunGong) | 🎮신형철<br/>[@ShinHyeongcheol](https://github.com/ShinHyeongcheol) | 🍑이지민<br/>[@vahkjsdf](https://github.com/vahkjsdf) | 🐰한유빈<br/>[@oilbeaneda](https://github.com/oilbeaneda) |
| :---: | :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/6f6be45f-f50e-4524-85ee-04d18de151ca" height="200" /> | <img src="https://github.com/user-attachments/assets/73ae097d-a726-4f73-b626-e76e0303c125" height="200" /> | <img src="https://github.com/user-attachments/assets/97c337ad-cc2d-4c71-93f2-24018106ce26" height="200" /> | <img src="https://github.com/user-attachments/assets/f24a040c-50e9-4c6a-85bd-6eb8941cd572" height="200" /> |
| `매칭 관리`,`매칭 결과 작성`,`알림` | `홈` | `온보딩`,`로그인`,`매칭 탐색` | `프로필`,`매칭 결과 확인` |

<br/>

## **⚒️ Tech Stacks**

| 항목 | 기술 스택 |
| :--- | :--- |
| Architecture | Google Recommended Architecture |
| Pattern | MVVM |
| DI | Hilt |
| Asynchronous | Coroutine, Flow |
| Network | Retrofit2, OkHttp |
| Navigation | Single Activity Architecture (SAA), Jetpack Navigation |
| UI Framework | Jetpack Compose |
| Image Processing | Coil, Lottie |
| Logging | Timber |

<br/>

> **📌 도입 기술 선정 이유**

**1️⃣ Architecture: Google Recommended Architecture** <br/>
서비스의 규모나 복잡도를 고려했을 때 도메인 레이어까지 고정적으로 가져가기보다는<br/>
기본적인 아키텍처 구조는 따르되, 도메인 레이어는 필요한 경우에만 유연하게 추가할 수 있도록 구성했습니다.
초기에는 가볍게 시작하고, 기능이 확장될수록 자연스럽게 구조를 확장해갈 수 있다고 판단했습니다.

**2️⃣ Pattern: MVVM (Model-View-ViewModel)** <br/>
팀원 대부분이 익숙하게 사용해온 패턴이며, ViewModel을 중심으로 상태를 관리하기 때문에 Jetpack Compose와의 궁합이 좋아 선택하게 되었습니다.
UI와 로직을 명확히 분리할 수 있어서 협업 시 역할 구분이 쉬워지고, 테스트나 유지보수 측면에서도 유리합니다.

**3️⃣ Dependency Injection: Hilt** <br/>
구글에서 권장하는 DI 라이브러리로, 보일러플레이트 코드를 줄이면서 의존성 관리의 일관성과 재사용성을 확보할 수 있어 도입했습니다.
또한 내부적으로 제공하는 컴포넌트들의 라이프사이클을 자동으로 관리해주기 때문에, 초기 설정 부담을 줄이고 생산성과 유지보수 효율을 높일 수 있습니다.

<br/>

## **📗 Convention**

📌 [컨벤션 문서 보러가기](https://www.notion.so/20221444hanyubin/2f0913d4ef84800ea953f5ad720c98ac?source=copy_link)

- **Github Convention**
- **Naming Convention**
- **Packaging Convention**

<br/>

## **🗂️ Project Structure**

```text
Google Recommned Architecture
🗃️ smashing.app
├─ 🗃️ core
│  ├─ 🗃️ common
│  │  ├─ 📁 navigation
│  │  ├─ 📁 state
│  │  └─ 📁 type
│  ├─ 🗃️ designsystem
│  │  ├─ 📁 component
│  │  └─ 📁 theme
│  ├─ 📁 extension
│  ├─ 🗃️ local
│  │  ├─ 📁 datastore
│  │  └─ 📁 room
│  ├─ 🗃️ network
│  └─ 📁 util
│
├─ 🗃️ data
│  ├─ 🗃️ di(feature 기반)
│  ├─ 🗃️ local
│  │  ├─ 📁 datasource
│  │  │  ├─ 📁 api
│  │  │  └─ 📁 impl
│  │  └─ 📁 entity
│  ├─ 🗃️ remote
│  │  ├─ 📁 datasource
│  │  │  ├─ 📁 api
│  │  │  └─ 📁 impl
│  │  ├─ 📁 dto
│  │  └─ 📁 service
│  ├─ 🗃️ mapper(dto ↔ model 변환)
│  ├─ 🗃️ model(feature 기반)
│  └─ 🗃️ repository
│     ├─ 📁 api
│     └─ 📁 impl
│
├─ 🗃️ domain
│  ├─ 📁 model
│  ├─ 📁 usecase
│  └─ 📁 mapper
│
└─ 🗃️ presentation
   ├─ 📁 main
   └─ 📁 home
      ...
