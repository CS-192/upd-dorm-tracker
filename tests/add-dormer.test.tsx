import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react-native";
import { DormerForm } from "./add-dormers";
import { Alert } from "react-native";

// Mock Firebase Firestore
jest.mock("@/FirebaseConfig", () => ({
  FIREBASE_DB: {},
}));

jest.mock("firebase/firestore", () => ({
  collection: jest.fn(),
  addDoc: jest.fn(),
}));

// Mock Alert
jest.spyOn(Alert, "alert");

// Mock DateTimePicker
jest.mock("@react-native-community/datetimepicker", () => ({
  DateTimePicker: jest.fn(({ value, onChange }) => (
    <button
      data-testid="mocked-date-picker"
      onClick={() =>
        onChange({
          type: "set",
          nativeEvent: { timestamp: new Date(2000, 0, 1).getTime() },
        })
      }
    >
      Mocked DateTimePicker
    </button>
  )),
}));

describe("DormerForm", () => {
  it("renders correctly", () => {
    const { getByText, getByPlaceholderText, getByTestId } = render(
      <DormerForm />
    );

    expect(getByText("Add New Dormer")).toBeTruthy();
    expect(getByPlaceholderText("First Name")).toBeTruthy();
    expect(getByPlaceholderText("Middle Initial")).toBeTruthy();
    expect(getByPlaceholderText("Last Name")).toBeTruthy();
    expect(getByPlaceholderText("Student Number (no dash)")).toBeTruthy();
    expect(getByTestId("birth-date-touchable")).toBeTruthy(); // Check for the TouchableOpacity
    expect(getByPlaceholderText("Phone Number")).toBeTruthy();
    expect(getByPlaceholderText("Dorm Name")).toBeTruthy();
    expect(getByPlaceholderText("Room Number")).toBeTruthy();
    expect(getByPlaceholderText("Address")).toBeTruthy();
  });

  it("validates required fields", async () => {
    const { getByText } = render(<DormerForm />);

    fireEvent.press(getByText("Add Dormer"));

    await waitFor(() => {});
  });

  it("submits the form successfully", async () => {
    const { getByText, getByPlaceholderText, getByTestId } = render(
      <DormerForm />
    );

    fireEvent.changeText(getByPlaceholderText("First Name"), "John");
    fireEvent.changeText(getByPlaceholderText("Middle Initial"), "D");
    fireEvent.changeText(getByPlaceholderText("Last Name"), "Doe");
    fireEvent.changeText(
      getByPlaceholderText("Student Number (no dash)"),
      "12345678"
    );
    fireEvent.changeText(getByPlaceholderText("Phone Number"), "1234567890");
    fireEvent(getByTestId("sex-picker"), "onValueChange", "M");
    fireEvent.changeText(getByPlaceholderText("Dorm Name"), "Dorm A");
    fireEvent.changeText(getByPlaceholderText("Room Number"), "101");
    fireEvent.changeText(getByPlaceholderText("Address"), "123 Main St");

    fireEvent.press(getByText("Add Dormer"));

    await waitFor(() => {
      expect(Alert.alert).toHaveBeenCalledWith(
        "Success",
        "Dormer added successfully!"
      );
    });
  });

  it("handles form submission error", async () => {
    const { getByText, getByPlaceholderText, getByTestId } = render(
      <DormerForm />
    );

    // Mock addDoc to throw an error
    const { addDoc } = require("firebase/firestore");
    addDoc.mockRejectedValueOnce(new Error("Failed to add dormer"));

    fireEvent.changeText(getByPlaceholderText("First Name"), "John");
    fireEvent.changeText(getByPlaceholderText("Middle Initial"), "D");
    fireEvent.changeText(getByPlaceholderText("Last Name"), "Doe");
    fireEvent.changeText(
      getByPlaceholderText("Student Number (no dash)"),
      "12345678"
    );
    fireEvent.changeText(getByPlaceholderText("Phone Number"), "1234567890");
    fireEvent(getByTestId("sex-picker"), "onValueChange", "M");
    fireEvent.changeText(getByPlaceholderText("Dorm Name"), "Dorm A");
    fireEvent.changeText(getByPlaceholderText("Room Number"), "101");
    fireEvent.changeText(getByPlaceholderText("Address"), "123 Main St");

    fireEvent.press(getByText("Add Dormer"));

    await waitFor(() => {
      expect(Alert.alert).toHaveBeenCalledWith(
        "Error",
        "Failed to add dormer."
      );
    });
  });
});
