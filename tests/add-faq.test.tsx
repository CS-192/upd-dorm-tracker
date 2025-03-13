import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react-native";
import AddFAQ from "./add-faq"; // Adjust the import path if needed
import { useRouter } from "expo-router";
import { addDoc } from "firebase/firestore";
import { getAuth } from "firebase/auth";

// Mock Firebase functions
jest.mock("firebase/firestore", () => ({
  collection: jest.fn(),
  addDoc: jest.fn(),
}));

jest.mock("firebase/auth", () => ({
  getAuth: jest.fn(() => ({
    currentUser: { uid: "test-user-id" },
  })),
}));

// Mock Expo Router
jest.mock("expo-router", () => ({
  useRouter: jest.fn(),
}));

describe("AddFAQ Component", () => {
  let mockRouter: { back: jest.Mock }; // ✅ Explicitly typed

  beforeEach(() => {
    mockRouter = { back: jest.fn() }; // ✅ No implicit 'any' error
    (useRouter as jest.Mock).mockReturnValue(mockRouter);
  });

  it("renders the form fields", () => {
    const { getByText } = render(<AddFAQ />);

    expect(getByText("Subject")).toBeTruthy();
    expect(getByText("Details")).toBeTruthy();
  });

  it("shows validation errors when submitting empty form", async () => {
    const { getByText, getByRole } = render(<AddFAQ />);

    fireEvent.press(getByRole("button", { name: /submit/i }));

    await waitFor(() => {
      expect(getByText("*This is required")).toBeTruthy();
    });
  });

  it("submits the form when valid", async () => {
    const { getByPlaceholderText, getByRole } = render(<AddFAQ />);

    fireEvent.changeText(
      getByPlaceholderText("Enter subject"),
      "Test Question"
    );
    fireEvent.changeText(getByPlaceholderText("Enter details"), "Test Answer");

    fireEvent.press(getByRole("button", { name: /submit/i }));

    await waitFor(() => {
      expect(addDoc).toHaveBeenCalled();
    });

    expect(mockRouter.back).toHaveBeenCalled();
  });

  it("navigates back when cancel is pressed", () => {
    const { getByRole } = render(<AddFAQ />);

    fireEvent.press(getByRole("button", { name: /cancel/i }));

    expect(mockRouter.back).toHaveBeenCalled();
  });
});
