
import { SubmitHandler, useForm, Controller } from "react-hook-form";
import { Dorm_Info } from "@/project_types";
import { TextInput, View, Text, StyleSheet, TouchableOpacity, ScrollView } from "react-native";
import { FIREBASE_DB } from '../FirebaseConfig';
import { updateDoc, doc, getDoc } from 'firebase/firestore';
import { getAuth } from 'firebase/auth';
import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect } from "react";
import Toast from "react-native-toast-message";





const EditDormInfoForm = () => {
    const { id } = useLocalSearchParams();
    const router = useRouter()
    const auth = getAuth();
    const user = auth.currentUser;
    const {
        control,
        handleSubmit,
        formState: { errors, isSubmitting },
        reset,
    } = useForm<Dorm_Info>();

    useEffect(()=>{
        const fetchData = async() => {
            const dormInfoDoc = doc(FIREBASE_DB, 'dorm-info', id as string);
            const data = await getDoc(dormInfoDoc)
            reset({
                dorm: data.data()?.dorm,
                curfew: data.data()?.curfew,
                address: data.data()?.address,
                contact_details: data.data()?.contact_details,
                history: data.data()?.history,
              });
        };
        fetchData();
    }, [id])

    const onSubmit: SubmitHandler<Dorm_Info> = async (data) => {
        if (user) {
            console.log(data)
            await updateDoc(doc(FIREBASE_DB, 'dorm-info', id as string), 
                { dorm: data.dorm,
                curfew: data.curfew,
                address: data.address,
                contact_details: data.contact_details,
                history: data.history
                });
            router.back()
            Toast.show({
                type: "success",
                text1: "Success!",
                text2: "Changes saved."
                });
        } else {
            console.log("No user logged in");
        }
    };

    // const onSubmit: SubmitHandler<Dorm_Info> = async (data) => {
    //         const d = new Date()
    //         if (user) {
    //             console.log(data)
    //             await addDoc(dormInfoCollection, 
    //                 { dorm: data.dorm,
    //                 curfew: data.curfew,
    //                 address: data.address,
    //                 contact_details: data.contact_details,
    //                 history: data.history,
    //                 userId: user.uid }); 
    //             reset();
    //             router.back()
    //             //navigation.navigate("announcement")  
    //           //fetchTodos();
    //         } else {
    //           console.log("No user logged in");
    //         }
    //       };
    return (
        <ScrollView style={styles.scrollviewContainer}>
            <Text style={styles.label}>Dorm</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputDetails}
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="dorm"
                rules={{ required: "*This is required" }}
            />
            {errors.dorm && <Text style={styles.errormessage}>{errors.dorm.message}</Text>}
            <Text style={styles.label}>Curfew</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputDetails}
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="curfew"
                rules={{ required: "*This is required" }}
            />
            {errors.curfew && <Text style={styles.errormessage}>{errors.curfew.message}</Text>}
            <Text style={styles.label}>Address</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputDetails}
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="address"
                rules={{ required: "*This is required" }}
            />
            {errors.address && <Text style={styles.errormessage}>{errors.address.message}</Text>}
            <Text style={styles.label}>Contact Details</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputDetails}
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="contact_details"
                rules={{ required: "*This is required" }}
            />
            {errors.contact_details && <Text style={styles.errormessage}>{errors.contact_details.message}</Text>}
            <Text style={styles.label}>History</Text>
            <Controller
                control={control}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                    style={styles.inputHistory}
                    onBlur={onBlur}
                    onChangeText={onChange}
                    value={value}
                    multiline={true}
                    scrollEnabled={true}
                    />
                )}
                name="history"
                rules={{ required: "*This is required" }}
            />
            {errors.history && <Text style={styles.errormessage}>{errors.history.message}</Text>}
                <View style={styles.bottomContainer}>
                    <TouchableOpacity style={styles.button} onPress={handleSubmit(onSubmit)} disabled={isSubmitting}>
                        <Text style={styles.buttonlabel}>Save</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={()=>router.back()}>
                        <Text style={styles.buttonlabel}>Cancel</Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
    );
}

    


const styles = StyleSheet.create({
    scrollviewContainer:{
        flex: 1,
    },
    inputDetails:{
        width: "100%",
        height: 80,
        borderWidth: 1,
        borderColor: "#d9d9d9",
        borderRadius: 8,
        paddingHorizontal: 12,
        textAlignVertical: "top",
    },  
    inputHistory:{
        width: "100%",
        height: 300,
        borderWidth: 1,
        borderColor: "#d9d9d9",
        borderRadius: 8,
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
    },
    buttonlabel: {
        fontSize: 18,
        color: "black",
        fontWeight: "bold",
    },
    bottomContainer: {
        justifyContent: "flex-end", 
        alignItems: "center",
        paddingBottom: 20,
    },
    errormessage:{
        fontSize: 14,
        color: "red",
        fontStyle: "italic",
    }
});

export default EditDormInfoForm;
