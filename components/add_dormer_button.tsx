import React from 'react';
import { TouchableOpacity, Text, View } from 'react-native';
import { useRouter, Link } from 'expo-router';
import styles from '@/app/styles';

const AddDormerButton = () => {
    return (
        <TouchableOpacity
            // onPress={should go to add dormer page}
            style={styles.addDormerButton}>
            {/* <Link href= `../${path}`> */}
                <Text style={styles.addDormerButtonText}>Add Dormer</Text>
            {/* </Link> */}
        </TouchableOpacity>
    );
  };
  
  export default AddDormerButton;
  