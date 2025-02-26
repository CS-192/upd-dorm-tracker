import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';  // Import the View and Text components
import { Table, TableWrapper, Row, Cell } from 'react-native-table-component';  // Import the Table components
import styles from '@/app/styles';  // Import the styles






const RequestTypes = () => {
  return (
    <View style={{flexDirection: 'row', marginBottom: 10, marginTop: 10}}>
      <TouchableOpacity style={styles.cell}>
        <Text>
          Late Night/Overnight Pass
        </Text>
      </TouchableOpacity>              
    
      <TouchableOpacity style={styles.cell}>
        <Text>
          Monthly Bill
        </Text>
    </TouchableOpacity>              
    <TouchableOpacity style={styles.cell}>
      <Text>
        Report
      </Text>
    </TouchableOpacity>              
    </View>
  );
}

export default RequestTypes;  