import React from "react";
import { Text } from "react-native";

interface PtextProps {
  children: React.ReactNode; // Correctly define children type
}

const Ptext: React.FC<PtextProps> = ({ children }) => {
  return <Text style={{ fontFamily: "Inter" }}>{children}</Text>;
};

export default Ptext;
