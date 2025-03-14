import { Stack } from "expo-router";
import HeaderWithMenu from "@/components/headerWithMenu";

export default function DormInfoLayout() {
  return (
    <>
      <HeaderWithMenu userRole="admin">
        <Stack
            screenOptions={{
            headerShown: false,
          }}>
            <Stack.Screen name="index" />
            <Stack.Screen name="announcement" />
            <Stack.Screen name="faq" />
            <Stack.Screen name="dorm-info" />
            <Stack.Screen name="create-announcement" />
            <Stack.Screen name="create-faq" />
            <Stack.Screen name="edit-announcement" />
            <Stack.Screen name="edit-faq" />
            <Stack.Screen name="edit-dorm-info" />
        </Stack>
        </HeaderWithMenu>    
    </>
    
  );
}
