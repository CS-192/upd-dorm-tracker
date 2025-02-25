import React from 'react';
import { View, Text } from 'react-native';  // Import the View and Text components
import { Table, TableWrapper, Row, Cell } from 'react-native-table-component';  // Import the Table components
import styles from '@/app/styles';  // Import the styles





const RequestTypes = () => {
  return (
    
    <View style={styles.tableContainer}>
        <Table borderStyle={{ borderBottomWidth: 1, borderColor: "#757575" }}>
          <TableWrapper style={styles.row}>
            <Cell data="Late night/Overnight Pass" style={styles.cell} textStyle={styles.requestTypesText} />
            <Cell data="Monthly Bill" style={styles.cell} textStyle={styles.requestTypesText} />
            <Cell data="Report" style={styles.cell} textStyle={styles.requestTypesText} />
          </TableWrapper>
        </Table>
    </View>
    
  );
}

export default RequestTypes;  // Export the TableComponent function