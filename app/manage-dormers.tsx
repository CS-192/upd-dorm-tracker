import React from "react";
import { SafeAreaView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import HeaderWithMenu from "@/components/headerWithMenu";
import styles from "./styles";
import SearchComponent from "@/components/requests_search";
import DormersTableComponent from "@/components/dormers_table";
import AddDormerButton from "@/components/add_dormer_button";


const ManageDormers = () => {
  return (
    <SafeAreaProvider>
        <HeaderWithMenu userRole="admin">
            <SafeAreaView style={styles.manageDormersContainer}>
                <Text style={styles.tabHeader}>Manage Dormer's Information</Text>
                <SearchComponent />
                <View style={{flex: 1, paddingBottom: 150}}>
                    <DormersTableComponent />
                </View>
                <AddDormerButton />
            </SafeAreaView>
        </HeaderWithMenu>
    </SafeAreaProvider>
  );
};


export default ManageDormers;
