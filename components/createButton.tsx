

import { useRouter, Link } from 'expo-router';
import React from 'react';
import { Text, TouchableOpacity, StyleSheet } from 'react-native';

interface ButtonProps {
    path: string;
    }

const CreateButton: React.FC<ButtonProps> = (ButtonProps) => {
    return (
        <TouchableOpacity
            style={styles.createButton}>
            <Link href={`../manage-dorm-details/create-${ButtonProps.path}`}>
                    <Text style={styles.createButtonText}>+   Create new</Text>
             </Link>
        </TouchableOpacity>
    );
};

export default CreateButton;

const styles = StyleSheet.create({
    createButton: {
        backgroundColor: 'transparent',
        borderColor: "#CACACA",
        borderWidth: 1,
        width: "40%",
        padding: 10,
        borderRadius: 100,
        alignItems: "center",
        marginTop: 15,
    },
    createButtonText: {
        color: "#000",
        fontSize: 14,
    },
});
