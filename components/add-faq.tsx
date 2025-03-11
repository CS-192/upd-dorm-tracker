
import { SubmitHandler, useForm, Controller } from "react-hook-form";

import { FAQ } from "@/project_types";
import { TextInput, Button, View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { FIREBASE_DB } from '../FirebaseConfig';
import { collection, addDoc } from 'firebase/firestore';
import { getAuth } from 'firebase/auth';
import { useRouter } from "expo-router";

const AddFAQ = () => {
    const auth = getAuth();
    const user = auth.currentUser;
    const router = useRouter();

    //const navigation = useNavigation();
    const dormDetailsCollection = collection(FIREBASE_DB, 'dorm-details');
    const {
        control,
        handleSubmit,
        formState: { errors, isSubmitting },
        reset,
    } = useForm<FAQ>();

    const onSubmit: SubmitHandler<FAQ> = async (data) => {
        const d = new Date()
        if (user) {
            console.log(data)
            await addDoc(dormDetailsCollection, 
                { question: data.question, 
                type: "FAQ",
                answer: data.answer,
                userId: user.uid }); 
            reset();
            router.back();
            //navigation.navigate("announcement")  
          //fetchTodos();
        } else {
          console.log("No user logged in");
        }
        
      };

    

    return (
        <View style={styles.container}>
            <Text style={styles.label}>Subject</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputSubject}
                    //placeholder="Subject"
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="question"
                rules={{ required: "*This is required" }}
            />
            {errors.question && <Text style={styles.errormessage}>{errors.question.message}</Text>}
            <Text style={styles.label}>Details</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputDetails}
                    //placeholder="Details"
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="answer"
                rules={{ required: "*This is required" }}
            />
            {errors.answer && <Text style={styles.errormessage}>{errors.answer.message}</Text>}
            <View style={styles.bottomContainer}>
                <TouchableOpacity style={styles.button} onPress={handleSubmit(onSubmit)} disabled={isSubmitting}>
                    <Text style={styles.buttonlabel}>Submit</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={()=>router.back()}>
                    <Text style={styles.buttonlabel}>Cancel</Text>
                </TouchableOpacity>
            </View>
            
        </View>
    );
    };
    


const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 20,
        //backgroundColor: "green",
    },
    inputSubject:{
        width: "100%",
        height: 80,
        borderWidth: 1,
        borderColor: "#d9d9d9",
        borderRadius: 8,
        marginTop: 5,
        paddingHorizontal: 12,
        //backgroundColor: "#fff",
        textAlignVertical: "top",
        //marginBottom: 16,
    },
    inputDetails:{
        width: "100%",
        height: 300,
        borderWidth: 1,
        borderColor: "#d9d9d9",
        borderRadius: 8,
        //marginBottom: 16,
        paddingHorizontal: 12,
        textAlignVertical: "top",
    },  
    label: {
        fontSize: 16,
        color: "black",
        marginBottom: 8,
        marginTop: 25,
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
    bottomContainer: {
        justifyContent: "flex-end", // Moves button to the bottom
        alignItems: "center",
        //backgroundColor: "black",
        flex: 1,
        paddingBottom: 20,
    },
    errormessage:{
        fontSize: 14,
        color: "red",
        fontStyle: "italic",
    }
});

export default AddFAQ;
