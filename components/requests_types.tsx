import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';  // Import the View and Text components
import { Table, TableWrapper, Row, Cell } from 'react-native-table-component';  // Import the Table components
import styles from '@/app/styles';  // Import the styles






const RequestTypes = () => {
  return (
    <View style={{flexDirection: 'row', marginBottom: 10, marginTop: 10}}>
      <TouchableOpacity style={{borderBottomWidth: 1, borderBottomColor: '#b2b2b2'}}>
        <Text style={{ ...styles.text, marginLeft: 0 }}>
          Late Night/Overnight Pass
        </Text>
      </TouchableOpacity>              
    
      <TouchableOpacity style={{borderBottomWidth: 1, borderBottomColor: '#b2b2b2'}}>
        <Text style={styles.text}>
          Monthly Bill
        </Text>
      </TouchableOpacity>              
      <TouchableOpacity style={{borderBottomWidth: 1, borderBottomColor: '#b2b2b2'}}>
        <Text style={styles.text}>
          Report
        </Text>
      </TouchableOpacity>              
    </View>
  );
}

export default RequestTypes;  