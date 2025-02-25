import { Login } from "@/app/login";
import { View } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import {createNativeStackNavigator} from '@react-navigation/native-stack';

// Entry point of the app.
// This is what will load when the app is opened
const Stack=createNativeStackNavigator();
const InsideStack=createNativeStackNavigator();


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
