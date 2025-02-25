import React, { useState } from "react";
import { View } from "react-native";
import { SearchBar } from "react-native-elements";
import styles from "@/app/styles";

const SearchComponent = () => {
  const [search, setSearch] = useState("");

  const updateSearch = (text:string) => {
    setSearch(text);
  };

  return (
    <View style={styles.searchContainer}>
      <SearchBar
        placeholder="Search here..."
        onChangeText={updateSearch}
        value={search}
        platform="default" 
        containerStyle={{
            backgroundColor: "transparent", // Removes default background
            borderTopWidth: 0, // Removes top border
            borderBottomWidth: 0, // Removes bottom border
            height: 36, // Adjust height
            padding: 0, // Removes default padding
            width: '100%',
            
          }}
        inputContainerStyle={{
            flex: 1, // Places the icon on the right
            backgroundColor: "#F5F5F5", // Change input background color
            borderRadius: 10, // Rounded corners
            height: 36, // Adjust height
            borderWidth: 1,
            borderBottomWidth: 1,
            borderColor: '#757575',
            width: '100%',

          }}
        inputStyle={{
            fontSize: 13, // Adjust font size
            height: 36, // Adjust height
            padding: 0, // Removes default padding
            color: '#000000', // Change text color
            }}
        
      />
    </View>
  );
};

export default SearchComponent;
