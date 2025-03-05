import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  Button,
  Alert,
  StyleSheet,
  SafeAreaView,
  TouchableHighlight,
  TouchableOpacity,
} from "react-native";
import { useForm, Controller } from "react-hook-form";
import { collection, addDoc } from "firebase/firestore";
import {
  GestureHandlerRootView,
  ScrollView,
} from "react-native-gesture-handler";
import { FIREBASE_DB } from "@/FirebaseConfig";
import { Dormer } from "@/project_types";
import Ptext, { Ptitle } from "@/project_components";

// DormerForm Component
const DormerForm: React.FC = () => {
  const { control, handleSubmit, reset } = useForm<Dormer>();
  const [loading, setLoading] = useState(false);

  const onSubmit = async (data: Dormer) => {
    try {
      setLoading(true);

      // Convert birth_date to Firestore Timestamp
      const birthDate = new Date(data.birth_date);

      // Add dormer entry to Firestore
      await addDoc(collection(FIREBASE_DB, "dormers"), {
        ...data,
        birth_date: birthDate.toISOString(), // Store as string for simplicity
      });

      Alert.alert("Success", "Dormer added successfully!");
      reset(); // Reset form after submission
    } catch (error) {
      console.error("Error adding dormer:", error);
      Alert.alert("Error", "Failed to add dormer.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Ptitle>Add New Dormer</Ptitle>

      <Controller
        control={control}
        name="student_id"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Student ID"
            keyboardType="numeric"
            onChangeText={(text) => onChange(Number(text))}
            value={value ? String(value) : ""}
          />
        )}
      />

      <Controller
        control={control}
        name="last_name"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Last Name"
            onChangeText={onChange}
            value={value}
          />
        )}
      />

      <Controller
        control={control}
        name="first_name"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="First Name"
            onChangeText={onChange}
            value={value}
          />
        )}
      />

      <Controller
        control={control}
        name="middle_initial"
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Middle Initial"
            maxLength={1}
            onChangeText={onChange}
            value={value}
          />
        )}
      />

      <Controller
        control={control}
        name="birth_date"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Birth Date (YYYY-MM-DD)"
            onChangeText={onChange}
            value={value ? String(value) : ""}
          />
        )}
      />

      <Controller
        control={control}
        name="phone_number"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Phone Number"
            keyboardType="numeric"
            onChangeText={(text) => onChange(Number(text))}
            value={value ? String(value) : ""}
          />
        )}
      />

      <Controller
        control={control}
        name="dorm"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Dorm Name"
            onChangeText={onChange}
            value={value}
          />
        )}
      />

      <Controller
        control={control}
        name="room_number"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Room Number"
            onChangeText={onChange}
            value={value}
          />
        )}
      />

      <Controller
        control={control}
        name="address"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Address"
            onChangeText={onChange}
            value={value}
          />
        )}
      />

      <TouchableOpacity
        onPress={handleSubmit(onSubmit)}
        disabled={loading}
        style={styles.button}
      >
        <Ptext>{loading ? "Saving..." : "Add Dormer"}</Ptext>
      </TouchableOpacity>
    </ScrollView>
  );
};

const AddDormers = () => {
  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <SafeAreaView style={{ flex: 1 }}>
        <DormerForm />
      </SafeAreaView>
    </GestureHandlerRootView>
  );
};

export default AddDormers;

const styles = StyleSheet.create({
  container: {
    padding: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 10,
  },
  input: {
    height: 45,
    borderWidth: 1,
    borderColor: "#ccc",
    padding: 10,
    borderRadius: 5,
    marginBottom: 10,
    marginVertical: 10,
    marginHorizontal: 50,
  },
  button: {
    borderColor: "#4e4f52",
    borderWidth: 1,
    paddingHorizontal: 20,
    paddingVertical: 6,
    margin: "auto",
    marginTop: 10,
    borderRadius: 5,
    width: 200,
  },
});
