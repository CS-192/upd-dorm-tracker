import { View, Text, StyleSheet, TouchableOpacity, FlatList, Alert } from "react-native";
import { FIREBASE_DB } from '../FirebaseConfig';
import { collection, getDocs, deleteDoc, doc, query, where, } from 'firebase/firestore';
import { getAuth } from 'firebase/auth';
import { useState } from "react";
import { useFocusEffect, useRouter} from "expo-router";
import React from "react";






const Card = ({subject, details, id, date}: {subject:string, details:string, id:string, date: Date}) => {
    const router = useRouter()
    const editAnnouncement = (id: string) => {
        router.push({pathname: "../manage-dorm-details/edit-announcement", params: {id}})
    }

    const deleteItem = async (id: string) => {
        const announcementDoc = doc(FIREBASE_DB, 'dorm-details', id);
        await deleteDoc(announcementDoc);
    };

    const deleteItemAlert = (id: string) => {
        Alert.alert(
          "Confirm Delete",
          "Are you sure you want to delete this item?",
          [
            { text: "Cancel", style: "cancel" },
            { text: "Delete", onPress: () => deleteItem(id), style: "destructive" }
          ]
        );
      };
    
    return(
        <View style={styles.card}>
            <View style={styles.subjectContainer}>
                <Text style={styles.subjectText}>{subject}</Text>
            </View>
            <View style={styles.dateContainer}>
                <Text style={styles.dateText}>{date.toString()}</Text>
            </View>
            <View style={styles.detailsContainer}>
                <Text>{details}</Text>
            </View>
            <View style={styles.buttonsContainer}>
                <TouchableOpacity style={styles.button} onPress={()=>deleteItemAlert(id)}>
                    <Text>Delete</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={()=>editAnnouncement(id)}>
                    <Text>Edit</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};



const DisplayAnnouncement: React.FC = () => {
    const auth = getAuth();
    const user = auth.currentUser;
    const [announcements, setAnnouncements] = useState<any>([]);
    const dormDetailsCollection = collection(FIREBASE_DB, 'dorm-details');

    // Updates the list of announcements displayed whenever screen is displayed
    useFocusEffect(
        React.useCallback(() => {
          fetchAnnouncements();
        }, [announcements])
      );

    const fetchAnnouncements = async () => {
        if (user) {
          const q = query(dormDetailsCollection, where("userId", "==", user.uid), // change to dorm once may profile na sa database
            where("type", "==", "Announcement"),

        );
          const data = await getDocs(q);
          setAnnouncements(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        } else {
          console.log("No user logged in");
        }
    };

    return(
        <FlatList
          data={announcements}
          renderItem={({ item }) => (
            <Card subject={item.subject} details={item.details} id={item.id} date={item.date}/>
          )}
          keyExtractor={(item) => item.id}
        />
    );
};

export default DisplayAnnouncement;

const styles = StyleSheet.create({
    card:{
        marginTop: 5,
        marginBottom: 3,
        borderColor: "#bbb",
        borderWidth: 1,
        height: 200,
        width: "100%",
    },
    subjectContainer:{
        //backgroundColor: "blue",
        width: "90%",
        flex:1,
        justifyContent: "center",
        alignSelf: "center"
    },
    detailsContainer:{
        width: "90%",
        //backgroundColor:"orange",
        flex: 2,
        alignSelf:"center",
        paddingBottom: 25,
    },
    buttonsContainer:{
        //backgroundColor:"green",
        width: "90%",
        flex:1,
        flexDirection: "row-reverse",
        alignSelf: "center",
    },
    button:{
        marginLeft: 15,
        width: 70,
        height: 35,
        borderWidth: 1,
        borderColor: "#bbb",
        alignItems:"center",
        justifyContent: "center",
        borderRadius: 100,
    },
    subjectText:{
        fontSize: 24,
    },
    detailsText:{
        fontSize:14,
    },
    dateContainer:{
        //backgroundColor: "blue",
        width: "90%",
        flex:0.5,
        justifyContent: "center",
        alignSelf: "center"
    },
    dateText:{
        fontSize: 12,
    }


})