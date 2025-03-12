import React, { useState } from "react";
import {
  View,
  TextInput,
  Alert,
  StyleSheet,
  SafeAreaView,
  TouchableOpacity,
  Platform,
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
import { Picker } from "@react-native-picker/picker";
import DateTimePicker from "@react-native-community/datetimepicker";

// DormerForm Component
export const DormerForm: React.FC = () => {
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
        name="student_id"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <TextInput
            style={styles.input}
            placeholder="Student Number (no dash)"
            keyboardType="numeric"
            onChangeText={(text) => onChange(Number(text))}
            value={value ? String(value) : ""}
          />
        )}
      />

      <Controller
        control={control}
        name="birth_date"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => {
          const [showPicker, setShowPicker] = useState(false);

          return (
            <View>
              {/* Open Date Picker */}
              <TouchableOpacity
                onPress={() => setShowPicker(true)}
                style={styles.input}
                testID="birth-date-touchable"
              >
                <Ptext
                  style={{ fontSize: 13.5, color: value ? "#000" : "#757575" }}
                >
                  {value ? new Date(value).toLocaleDateString() : "Birth Date"}
                </Ptext>
              </TouchableOpacity>

              {/* Show Date Picker only when requested */}
              {showPicker && (
                <DateTimePicker
                  value={value ? new Date(value) : new Date()}
                  mode="date"
                  display={Platform.OS === "ios" ? "spinner" : "default"}
                  testID="datePicker"
                  onChange={(event, selectedDate) => {
                    setShowPicker(false);
                    if (selectedDate) {
                      onChange(selectedDate.toISOString()); // Store in ISO format for Firestore
                    }
                  }}
                />
              )}
            </View>
          );
        }}
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
        name="sex"
        rules={{ required: true }}
        render={({ field: { onChange, value } }) => (
          <View style={styles.pickerContainer}>
            <Picker
              selectedValue={value}
              onValueChange={onChange}
              style={styles.picker}
              testID="sex-picker"
            >
              <Picker.Item
                label="Sex"
                value=""
                style={{ fontSize: 13.5, color: "#757575" }}
              />
              <Picker.Item label="Male" value="M" />
              <Picker.Item label="Female" value="F" />
            </Picker>
          </View>
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
        <Ptext>{loading ? "Loading..." : "Add Dormer"}</Ptext>
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
    height: 50,
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
  pickerContainer: {
    marginHorizontal: 50,
    marginBottom: 10,
    marginVertical: 10,
    borderRadius: 5,
    borderColor: "#ccc",
    borderWidth: 1,
  },
  picker: {
    padding: 0,
    height: 50,
    fontSize: 1,
  },
});
