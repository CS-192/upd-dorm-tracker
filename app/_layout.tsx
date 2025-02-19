import { useFonts } from "expo-font";
import { Stack } from "expo-router";
import { View } from "react-native";
import Toast from "react-native-toast-message";

// This is where the "pages" are setup.
// When a new page is added add a new Stack.Screen with name being the file name of the page that will be added.
// For a more advance _layout check inside app-example.

export default function RootLayout() {
  const [fontsLoaded] = useFonts({
    Inter: require("../assets/fonts/Inter.ttf"), // Make sure the path is correct
  });

  if (!fontsLoaded) {
    return <View />; // Show loading screen until font is ready
  }

  return (
    <>
      <Stack
        screenOptions={{
          headerShown: false,
        }}
      >
        <Stack.Screen name="index" />
        <Stack.Screen name="dashboard" />
      </Stack>
      <Toast />
    </>
  );
}
