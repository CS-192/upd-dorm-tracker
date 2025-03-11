import React from "react";
import { SafeAreaView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import HeaderWithMenu from "@/components/headerWithMenu";
import styles from "./styles";
import SearchComponent from "@/components/requests_search";
import TableComponent from "@/components/requests_table";
import RequestTypes from "@/components/requests_types";


const ManageRequests = () => {
  return (
    <SafeAreaProvider>
      <HeaderWithMenu userRole="admin">
        <SafeAreaView style={styles.container}>
          <Text style={styles.tabHeader}>Manage requests</Text>
          <RequestTypes />
          <SearchComponent />
          <TableComponent />
        </SafeAreaView>
      </HeaderWithMenu>
    </SafeAreaProvider>
  );
};

export default ManageRequests;
