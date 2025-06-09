# Gemini Nano Android Sample

An Android sample project for running **Gemini Nano** on-device.  
This project uses Android AICore and the [Google AI Edge SDK](https://ai.google.dev/edge) to demonstrate invoking the Gemini Nano model locally on the device.

---

## ✨ Gemini Nano Overview

- **Gemini Nano** is a lightweight model with limited capabilities compared to cloud-based Gemini models, but it enables ultra-low-latency inference on-device.  
- It is available on Pixel 9 series devices running Android 14 or higher, leveraging the AICore system service to keep the model up to date and minimize latency.  
- The AICore and Gemini Nano APIs are provided via the [Google AI Edge SDK](https://ai.google.dev/edge).

> 📌 For more details: [Official Blog Announcement](https://android-developers.googleblog.com/2023/12/a-new-foundation-for-ai-on-android.html)

---

## 📦 Project Information

- Sample source: [ai-samples/gemini-nano](https://github.com/android/ai-samples/tree/main/gemini-nano)  
- Test device: **Pixel 9 series required** (Pixel 8 and Galaxy S24 are not officially supported at this time)  
- Key features: LLM download and on-device inference testing

---

## ⚙️ Prerequisites

1. Join the **[aicore-experimental Google Group](https://groups.google.com/g/aicore-experimental)**
2. Enroll in the **[Android AICore beta testing program](https://play.google.com/apps/testing/com.google.android.aicore)**  
   > Once enrolled, the app name in the Play Store will change to `Android AICore (Beta)`
3. **Update the required apps on the Play Store**  
   - [Android AICore](https://play.google.com/store/apps/details?id=com.google.android.aicore): version must start with `0.thirdpartyeap`  
   - [Private Compute Services](https://play.google.com/store/apps/details?id=com.google.android.as.oss): ensure version is `1.0.release.658389993` or higher

---

## ▶️ How to Run

1. Sign in to a Pixel 9 device that meets the above requirements with your beta tester account  
2. Build and run the sample project  
3. **Verify that the model is downloaded via the Download Manager**

---

## ❗ Notes and Caveats

- **This will not work on devices other than the Pixel 9 series**. References to Pixel 8 and Galaxy S24 from earlier documentation have been removed  
- If the same Google account is used across multiple devices, the AICore beta enrollment may conflict or fail to register  
- Real-world success cases:  
  - Registration failed with the existing account → **Created a new Google account and registered on a single device → Success**  
- Failure cases:  
  - After enrolling the existing Google account on Pixel 9 and updating AICore, the model failed to load

---

## 📚 References

- [Gemini Nano official overview & supported functionality](https://developer.android.com/ai/gemini-nano#supported-functionality)  
- [AICore system beta enrollment guide](https://play.google.com/apps/testing/com.google.android.aicore)  
- [Gemini Nano execution experience (Qiita)](https://qiita.com/takahirom/items/1c5f133ae0cfeb9b9517)  
- [Google AI Edge SDK blog](https://iurysouza.dev/ai-edge-sdk/)

---

## 🧪 Purpose

This practice-based example project is created to run the Gemini Nano model on Android’s AICore platform and learn how to utilize the Google AI Edge SDK.
