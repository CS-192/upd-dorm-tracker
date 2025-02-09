import Login from "@/components/login";
import { Text, View } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";

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
        <Text>Add Components Here:</Text>
        <Login />
      </SafeAreaView>
    </SafeAreaProvider>
  );
}
