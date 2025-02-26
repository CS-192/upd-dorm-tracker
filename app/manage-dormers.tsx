import React from "react";
import { SafeAreaView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import SearchComponent from "@/components/requests_search";
import DormersTableComponent from "@/components/dormers_table";
import AddDormerButton from "@/components/add_dormer_button";


const ManageDormers = () => {
  return (
    <SafeAreaProvider>
        <SafeAreaView style={styles.container}>
            <SafeAreaView style={styles.manageDormersContainer}>
                <Text style={styles.tabHeader}>Manage Dormer's Information</Text>
                <SearchComponent />
                <DormersTableComponent />
                <AddDormerButton />
            </SafeAreaView>

        </SafeAreaView>
    </SafeAreaProvider>
  );
};


export default ManageDormers;
