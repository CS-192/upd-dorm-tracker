import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react-native";
import AddAnnouncement from "./add-announcement";
import { Alert } from "react-native";
import { addDoc } from "firebase/firestore";
import { useRouter } from "expo-router";
import Toast from "react-native-toast-message";

// Mock Firebase Firestore
jest.mock("@/FirebaseConfig", () => ({
  FIREBASE_DB: {},
}));

jest.mock("firebase/firestore", () => ({
  collection: jest.fn(),
  addDoc: jest.fn(),
}));

jest.mock("expo-router", () => ({
    useRouter: () => ({
      push: jest.fn(),
      back: jest.fn(),
      isReady: true, // ✅ Mock `isReady`
    }),
  }));

// Mock Alert
jest.spyOn(Toast, "show");

jest.mock("firebase/auth", () => ({
    getAuth: jest.fn(() => ({
      currentUser: { uid: "testUser123" },
    })),
  }));


describe("AddAnnouncement", () => {
  it("renders correctly", () => {
    const { getByText, getByPlaceholderText, getByTestId } = render(
      <AddAnnouncement />
    );

    //expect(getByText("Add Announcement")).toBeTruthy();
    expect(getByText("Subject")).toBeTruthy();
    expect(getByText("Details")).toBeTruthy();
  });

  it("displays validation errors when details field  is empty", async () => {
    const { getByText, getByPlaceholderText } = render(<AddAnnouncement />);
    const subjectInput = getByPlaceholderText("Enter subject");
    const detailsInput = getByPlaceholderText(" Enter details");
    const saveButton = getByText("Save");

    // Fill inputs
    fireEvent.changeText(subjectInput, "Test Subject");
    //fireEvent.changeText(detailsInput, "Test Details");
    fireEvent.press(saveButton);
  
    // Check if validation errors appear
    await waitFor(() => {
      expect(getByText("*This is required")).toBeTruthy(); // Checks if required field error is displayed
    });
  });

  it("displays validation errors when subject field  is empty", async () => {
    const { getByText, getByPlaceholderText } = render(<AddAnnouncement />);
    const subjectInput = getByPlaceholderText("Enter subject");
    const detailsInput = getByPlaceholderText(" Enter details");
    const saveButton = getByText("Save");

    // Fill inputs
    //fireEvent.changeText(subjectInput, "Test Subject");
    fireEvent.changeText(detailsInput, "Test Details");
    fireEvent.press(saveButton);
  
    // Check if validation errors appear
    await waitFor(() => {
      expect(getByText("*This is required")).toBeTruthy(); // Checks if required field error is displayed
    });
  });

  it("renders input fields and handles form submission", async () => {
    const { getByText, getByPlaceholderText } = render(<AddAnnouncement />);
    const subjectInput = getByPlaceholderText("Enter subject");
    const detailsInput = getByPlaceholderText(" Enter details");
    const saveButton = getByText("Save");

    // Fill inputs
    fireEvent.changeText(subjectInput, "Test Subject");
    fireEvent.changeText(detailsInput, "Test Details");

    // Submit form
    fireEvent.press(saveButton);

    // Check if addDoc is called
    await waitFor(() => {
      expect(addDoc).toHaveBeenCalled();
    });

    // Check if Toast message is shown
    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "success",
        text1: "Success!",
        text2: "Announcement added successfully.",
    });
    });
  });
});