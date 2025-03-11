import { Dorm_Info } from "@/project_types";
import { TextInput, Button, View, Text, StyleSheet, TouchableOpacity, FlatList, Image} from "react-native";
import { FIREBASE_DB } from '../FirebaseConfig';
import { collection, addDoc, getDocs, updateDoc, deleteDoc, doc, query, where } from 'firebase/firestore';
import { getAuth } from 'firebase/auth';
import { useEffect, useState } from "react";
import { useFocusEffect, useRouter, useNavigation } from "expo-router";
import React from "react";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";



const molave1 = require("../assets/images/molave1.png");
const molave2 = require("../assets/images/molave2.jpg");
const molave3 = require("../assets/images/molave3.jpg");
const images = [
    {id: "1", image: molave1}, 
    {id: "2", image: molave2}, 
    {id: "3", image: molave3}
]

const DormPictures = () => {
    return (
        
        <View style={styles.imageContainer}>
            <FlatList
            data={images}
            keyExtractor={(item) => item.id}
            renderItem={({ item }) => (
                //<View style={styles.imageContainer}>
                <Image source={item.image } style={styles.image} resizeMode="cover" />
                //</View>
            )}
            horizontal
            pagingEnabled
            showsHorizontalScrollIndicator={true}
    />
        </View>
            
            
        
      );
    };



const Card = ({dorm, curfew, address, contact_details, history, id}: 
    {dorm:string, curfew:string, address:string, contact_details: string, history: string, id: string}) => {
    
    const router = useRouter()
    const editDormInfo = (id: string) => {
        router.push({pathname: "../manage-dorm-details/edit-dorm-info", params: {id}})
        }

    return(
        <View style={styles.container}>
            <View style={styles.infocontainer}>
                <View style={styles.infoCard}>
                    <Text style={styles.dormName}>{dorm}</Text>
                </View>
                <View style={styles.infoCard}>
                    <Text style={styles.header}>Curfew</Text>
                    <Text style={styles.content}>{curfew}</Text>
                </View>
                <View style={styles.infoCard}>
                    <Text style={styles.header}>Address</Text>
                    <Text style={styles.content}>{address}</Text>
                </View>
                <View style={styles.infoCard}>
                    <Text style={styles.header}>Contact Details</Text>
                    <Text style={styles.content}>{contact_details}</Text>
                </View>
                <View style={styles.infoCard}>
                    <Text style={styles.header}>History</Text>
                    <Text style={styles.content}>{history}</Text>
                </View>
            </View>
            
            <View style={styles.buttonContainer}>
                <TouchableOpacity onPress={()=>editDormInfo(id)} style={styles.button}>
                    <Text style={styles.buttonlabel}>Edit</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};



const DisplayDormInfo: React.FC = () => {
    const auth = getAuth();
    const user = auth.currentUser;
    const [dormInfo, setDormInfo] = useState<any>([]);
    const dormInfoCollection = collection(FIREBASE_DB, 'dorm-info');
    

    useFocusEffect(
        React.useCallback(() => {
          fetchDormInfo();
        }, [dormInfo])
      );

    
      
    
    const fetchDormInfo = async () => {
        if (user) {
          const q = query(dormInfoCollection, where("userId", "==", user.uid),
        );
          const data = await getDocs(q);
          setDormInfo(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        } else {
          console.log("No user logged in");
        }
    };

    
    return(
        <View style={styles.container}>
            <DormPictures/>
        <FlatList
          data={dormInfo}
          renderItem={({ item }) => (
            <Card dorm={item.dorm} curfew={item.curfew} address={item.address} 
                contact_details={item.contact_details} history={item.history}
                id={item.id}/>
            //<Card data={item}/>
          )}
          keyExtractor={(item) => item.id}
        />
        </View>
    );
};

export default DisplayDormInfo;

const styles = StyleSheet.create({
    container:{
        //backgroundColor:"yellow",
        flex: 1,
        justifyContent: "space-between"
    },
    infocontainer:{
        flex: 1,
        //backgroundColor:"yellow"
    },
    dormName: {
        fontSize: 30,
        fontWeight: "bold",
    },
    header: {
        fontSize: 20,
        fontWeight: "bold"
    },
    content:{
        fontSize: 16,
    },
    infoCard:{
        marginTop: 10,

    },
    imageContainer:{
        color: "blue",
        justifyContent: "center",
        alignItems: "center",
        alignSelf: "center",
        width: "100%",
        height: "30%",
        borderWidth: 1,
        borderColor: "black",
    },
    image:{
        width: undefined,
        height: undefined,
        aspectRatio: 1.5,
        flex:1,
        //width: "100%",
        //height: "40%",
    },
    button: {
        width: "60%",
        backgroundColor: "#f6f6f6",
        borderWidth: 1,
        borderColor: "#cacaca",
        height: 35,
        borderRadius: 8,
        alignItems: "center",
        justifyContent: "center",
        marginTop: 20,
        //alignSelf: "flex-end",
    },
    buttonlabel: {
        fontSize: 18,
        color: "black",
        fontWeight: "bold",
    },
    buttonContainer: {
        justifyContent: "flex-end", // Moves button to the bottom
        alignItems: "center",
        //backgroundColor: "black",
        //flex: 1,
        paddingBottom: 20,
    },
    
})