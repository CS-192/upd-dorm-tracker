import React, { useState } from "react";
import { TextInput } from "react-native";
import styles from "@/app/styles";

const SearchComponent = () => {
  const [search, setSearch] = useState("");

  const updateSearch = (text: string) => {
    setSearch(text);
  };

  return (
    <TextInput
          style={{...styles.input, borderColor: "#b2b2b2"}}
          placeholder="Search here..."
          value={search}
          onChangeText={(text) => updateSearch(text)}
    />
  );
};






export default SearchComponent;
