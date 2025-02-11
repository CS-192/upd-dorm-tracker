import { Login } from "@/components/login";
import { View } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";

// Entry point of the app.
// This is what will load when the app is opened
export default function Index() {
  return (
    <SafeAreaProvider>
      <SafeAreaView
        style={{
          flex: 1,
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Login />
      </SafeAreaView>
    </SafeAreaProvider>
  );
}