import { Stack } from "expo-router";

export default function DormInfoLayout() {
  return (
    <>
        <Stack
            screenOptions={{
            headerShown: false,
          }}>
            <Stack.Screen name="index" />
            <Stack.Screen name="announcement" />
            <Stack.Screen name="faq" />
            <Stack.Screen name="dorm-info" />
        </Stack>
    
    </>
    
  );
}
