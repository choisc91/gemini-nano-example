# Gemini Nano Android Sample

안드로이드 기기에서 **Gemini Nano**를 실행하는 샘플 프로젝트입니다.  
이 프로젝트는 Android AICore 및 [Google AI Edge SDK](https://ai.google.dev/edge?hl=ko)를 활용하여, 기기 내에서 Gemini Nano 모델을 호출하는 실습을 지원합니다.

---

## ✨ Gemini Nano 개요

- **Gemini Nano**는 클라우드 기반 Gemini 모델보다 기능은 제한적이지만, 기기 내에서 초저지연 추론이 가능한 경량 모델입니다.
- Android 14 이상을 실행하는 Pixel 9 시리즈에서 사용 가능하며, AICore 시스템 서비스를 통해 모델을 최신 상태로 유지하고 지연 시간을 줄입니다.
- AICore 및 Gemini Nano API는 [Google AI Edge SDK](https://ai.google.dev/edge?hl=ko)에서 제공합니다.

📌 자세한 설명: [공식 블로그 소개](https://android-developers.googleblog.com/2023/12/a-new-foundation-for-ai-on-android.html)

---

## 📦 프로젝트 정보

- 샘플 소스: [ai-samples/gemini-nano](https://github.com/android/ai-samples/tree/main/gemini-nano)
- 테스트 기기: **Pixel 9 시리즈 필수** (Pixel 8, Galaxy S24는 현재 공식 지원하지 않음)
- 주요 기능: LLM 다운로드, 로컬 추론 테스트

---

## ⚙️ 사전 준비 사항

1. **[aicore-experimental Google 그룹](https://groups.google.com/g/aicore-experimental?hl=ko)**에 가입
2. **[Android AICore 베타 테스트 프로그램](https://play.google.com/apps/testing/com.google.android.aicore?hl=ko)** 참여  
   > 가입 완료 시 Play 스토어에서 앱명이 `Android AICore (베타)`로 변경됩니다
3. **Play 스토어에서 필수 앱 업데이트**
   - [Android AICore](https://play.google.com/store/apps/details?id=com.google.android.aicore&hl=ko): 버전이 `0.thirdpartyeap`으로 시작해야 함
   - [Private Compute Services](https://play.google.com/store/apps/details?id=com.google.android.as.oss&hl=ko): 버전이 `1.0.release.658389993` 이상인지 확인

---

## ▶️ 실행 방법

1. 위 조건을 충족한 Pixel 9 기기에 베타 테스터 계정으로 로그인
2. 샘플 프로젝트를 빌드 및 실행
3. **Download Manager를 통해 모델이 다운로드되는지 확인**

---

## ❗ 특이사항 및 주의점

- **Pixel 9 외 기기에서는 작동하지 않습니다**. 초기 문서에 있던 Pixel 8, Galaxy S24 관련 문구는 현재 삭제됨
- 같은 구글 계정이 여러 단말기를 관리할 경우, AICore 베타 등록이 충돌하거나 누락될 수 있음
- 실제 실행 성공 사례:
  - 기존 계정으로 등록 실패 → **새 구글 계정 생성 및 단일 기기 등록 → 성공**
- 실패 사례:
  - Pixel 9에 기존 구글 계정 등록 후 AICore 업데이트 했지만 모델이 로딩되지 않음

---

## 📚 참고 문서

- [Gemini Nano 공식 소개 및 지원 기능](https://developer.android.com/ai/gemini-nano?hl=ko#supported-functionality)
- [AICore 시스템 베타 등록 안내](https://play.google.com/apps/testing/com.google.android.aicore?hl=ko)
- [Gemini Nano 실행 경험 공유 (Qiita)](https://qiita.com/takahirom/items/1c5f133ae0cfeb9b9517)
- [Google AI Edge SDK 블로그](https://iurysouza.dev/ai-edge-sdk/)

---

## 🧪 목적

이 프로젝트는 Android의 AICore 플랫폼에서 Gemini Nano 모델을 실제로 실행해보고, Google AI Edge SDK의 활용 방법을 학습하기 위해 제작된 실습 기반 예제입니다.
