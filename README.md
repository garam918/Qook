# [Technical Documentation] Qook Architecture & Implementation

## 1. Tech Stack
* **Language:** **Kotlin**
* **Architecture:** **Google's Recommended App Architecture** (MVVM + Repository)
* **Dependency Injection:** **Koin**
* **Local Database:** **Room**
* **Asynchronous Processing:** **Coroutines & Flow**
* **AI Model:** **Google Gemini 2.5 flash**
* **Subscription Management:** **RevenueCat SDK**
* **Backend/Auth:** **Firebase**

---

## 2. System Architecture
We followed **Google’s recommended app architecture** to ensure scalability and maintainability.

1. **UI Layer:** Uses **ViewModel** and **StateFlow** to handle UI states and react to data changes.
2. **Domain Layer (Optional):** Contains business logic and use cases for recipe processing.
3. **Data Layer:** * **Repository Pattern:** Acts as a single source of truth, mediating between Network (Gemini, YouTube API) and Local (Room) data sources.
    * **Room DB:** Stores saved recipes and shopping lists for offline access.

---

## 3. RevenueCat Implementation
We integrated RevenueCat to simplify the complex in-app purchase lifecycle.

* **Entitlements:** We defined a `Qook Pro` entitlement that unlocks "Unlimited AI Extractions."
 
* **Dynamic Paywalls:** Used RevenueCat to display optimized paywalls, checking user status before allowing Gemini-powered extractions.
