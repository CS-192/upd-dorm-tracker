import { Stack } from "expo-router";
import Toast from "react-native-toast-message";

// This is where the "pages" are setup.
// When a new page is added add a new Stack.Screen with name being the file name of the page that will be added.
// For a more advance _layout check inside app-example.

export default function RootLayout() {
  return (
    <>
      <Stack
        screenOptions={{
          headerShown: false,
        }}
      >
        <Stack.Screen name="index" />
        <Stack.Screen name="login"/>
        <Stack.Screen name="dashboard" />
      </Stack>
      <Toast />
    </>
  );
}
