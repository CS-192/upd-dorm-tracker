import React from "react";
import { Text, TextStyle } from "react-native";

interface PtextProps {
  children: React.ReactNode;
  style?: TextStyle; // Allow custom styles
}

const Ptext: React.FC<PtextProps> = ({ children, style }) => {
  return <Text style={[{ fontFamily: "Inter" }, style]}>{children}</Text>;
};

export const Ptitle: React.FC<PtextProps> = ({ children, style }) => {
  return (
    <Text
      style={[{ fontSize: 30, fontWeight: 800, fontFamily: "Inter" }, style]}
    >
      {children}
    </Text>
  );
};

export default Ptext;
