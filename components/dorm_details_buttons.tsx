import React from 'react';
import { TouchableOpacity, Text, View } from 'react-native';
import { useRouter, Link } from 'expo-router';
import styles from '@/app/styles';

interface DormDetailsButtonProps {
    pressHandler: () => void;
    label: string;
    }

const DormDetailsButton: React.FC<DormDetailsButtonProps> = (DormDetailsButtonProps) => {
    return (
        <TouchableOpacity
            onPress={() => DormDetailsButtonProps.pressHandler()}
            style={styles.dormDetailsButton}>
            {/* <Link href= `../${path}`> */}
                <Text style={styles.dormDetailsButtonText}>{DormDetailsButtonProps.label}</Text>
            {/* </Link> */}
        </TouchableOpacity>
    );
};

const DormDetailsButtons = () => {
    const router = useRouter();
    const handlePress = (path: string) => {
        router.push(`../${path}`);
    }

    return (
        <View>
            <DormDetailsButton  pressHandler={()=>handlePress("manage-dorm-details/announcement")} label= 'Announcements' />
            <DormDetailsButton pressHandler={()=>handlePress("manage-dorm-details/faq")} label= 'Frequently Asked Questions' />
            <DormDetailsButton pressHandler={()=>handlePress("manage-dorm-details/dorm-info")} label= 'Dorm Information' />
        </View>
    );
};

export default DormDetailsButtons;