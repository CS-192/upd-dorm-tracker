import Ptext from "@/project_components";
import { useLocalSearchParams } from "expo-router";
import { View } from "react-native";

export default function Dormer() {
  const { id } = useLocalSearchParams();

  return (
    <View>
      <Ptext>{id}</Ptext>
    </View>
  );
}
