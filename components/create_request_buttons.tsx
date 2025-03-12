import React from 'react';
import { TouchableOpacity, Text, View } from 'react-native';
import { useRouter } from 'expo-router';
import styles from '@/app/styles';

interface CreateRequestButtonProps {
    pressHandler: () => void;
    label: string;
}

const CreateRequestButton: React.FC<CreateRequestButtonProps> = ({ pressHandler, label }) => {
    return (
        <TouchableOpacity
            onPress={pressHandler}
            style={styles.dormDetailsButton}>
            <Text style={styles.dormDetailsButtonText}>{label}</Text>
        </TouchableOpacity>
    );
};

const CreateRequestButtons = () => {
    const router = useRouter();
    const handlePress = (path: string) => {
        router.push(`../create-request/${path}`);
    };

    return (
        <View>
            <CreateRequestButton pressHandler={() => handlePress("monthly-bill")} label="Monthly Bill" />
            <CreateRequestButton pressHandler={() => handlePress("late-pass")} label="Late/Overnight Pass" />
            <CreateRequestButton pressHandler={() => handlePress("report")} label="Reports/Concerns" />
        </View>
    );
};

export default CreateRequestButtons;
