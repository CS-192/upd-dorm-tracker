import React from "react";
import { SafeAreaView, Text, TouchableOpacity, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import SearchComponent from "@/components/requests_search";
import DormersTableComponent from "@/components/dormers_table";
import { Ptitle } from "@/project_components";
import { Link } from "expo-router";

const ManageDormers = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageDormersContainer}>
          <Ptitle>Manage Dormer's Information</Ptitle>
          <SearchComponent />
          <DormersTableComponent />
          <TouchableOpacity style={styles.addDormerButton}>
            <Link href="/add-dormers">
              <Text style={styles.addDormerButtonText}>Add Dormer</Text>
            </Link>
          </TouchableOpacity>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default ManageDormers;
