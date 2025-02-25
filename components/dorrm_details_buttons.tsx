import React from 'react';
import { TouchableOpacity,
    Text,
    View,
 } from 'react-native';
import styles from '@/app/styles';

const DormDetailsButtons = () => {
    const handlePress = () => {
        console.log('Button Pressed');
    };

    return (
        <View>
            <TouchableOpacity
                style={styles.dormDetailsButton} 
                onPress={handlePress}>
                <Text style={styles.dormDetailsButtonText}>Announcements</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.dormDetailsButton} 
                onPress={handlePress}>
                <Text style={styles.dormDetailsButtonText}>Frequently Asked Questions</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.dormDetailsButton} 
                onPress={handlePress}>
                <Text style={styles.dormDetailsButtonText}>Dorm Information</Text>
            </TouchableOpacity>
        </View>
           
        
    );
};



export default DormDetailsButtons;