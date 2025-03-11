import { Stack } from "expo-router";
import HeaderWithMenu from "@/components/headerWithMenu";

export default function CreateRequestLayout() {
  return (
    <>
      <HeaderWithMenu userRole="student">
        <Stack
            screenOptions={{
            headerShown: false,
          }}>
            <Stack.Screen name="index" />
            <Stack.Screen name="monthly-bill" />
            <Stack.Screen name="late-pass" />
            <Stack.Screen name="report" />
        </Stack>
      </HeaderWithMenu>    
    </>
  );
}