import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseAuth

@main
struct iOSApp: App {

    init() {

        FirebaseApp.configure()
        KoinKt.doInitKoinIos()

        checkAndForceLogoutOnFirstRun()

        // configureRevenueCat()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

    private func checkAndForceLogoutOnFirstRun() {
        let hasRunBeforeKey = "hasRunBefore"
        let userDefaults = UserDefaults.standard

        if !userDefaults.bool(forKey: hasRunBeforeKey) {
            do {

                try Auth.auth().signOut()
                print("First run detected: Forced Firebase sign out to clear keychain.")
            } catch {
                print("Error signing out on first run: \(error)")
            }

            userDefaults.set(true, forKey: hasRunBeforeKey)
        }
    }
}