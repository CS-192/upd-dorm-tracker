import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react-native";
import { Alert } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Login from "./login";
import { useRouter as useRouterOriginal } from "expo-router";
import Toast from "react-native-toast-message";
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
} from "firebase/auth";
import { FIREBASE_AUTH } from "@/FirebaseConfig";

// Mock the router
jest.mock("expo-router", () => ({
  useRouter: jest.fn(),
}));

// Mock AsyncStorage
jest.mock("@react-native-async-storage/async-storage", () => ({
  getItem: jest.fn(),
  setItem: jest.fn(),
}));

// Mock Firebase Auth
jest.mock("firebase/auth", () => ({
  createUserWithEmailAndPassword: jest.fn(),
  signInWithEmailAndPassword: jest.fn(),
}));

// Mock FirebaseConfig
jest.mock("@/FirebaseConfig", () => ({
  FIREBASE_AUTH: {},
}));

// Mock Toast
jest.mock("react-native-toast-message", () => ({
  show: jest.fn(),
}));

// Mock Alert
jest.mock("react-native/Libraries/Alert/Alert", () => ({
  alert: jest.fn(),
}));

// Mock image
jest.mock("../assets/images/logo_circle.png", () => "mocked-image-path");

// Mock console.log
const originalConsoleLog = console.log;
console.log = jest.fn();

describe("Login Component", () => {
  let router: { push: jest.Mock };

  beforeEach(() => {
    router = { push: jest.fn() };
    (useRouterOriginal as jest.Mock).mockReturnValue(router);
    jest.clearAllMocks();
  });

  // Restore console.log after all tests
  afterAll(() => {
    console.log = originalConsoleLog;
  });

  test("renders the login form correctly", () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    expect(getByPlaceholderText("Username")).toBeTruthy();
    expect(getByPlaceholderText("Password")).toBeTruthy();
    expect(getByText("Sign in")).toBeTruthy();
    expect(getByText("Forgot password?")).toBeTruthy();
    expect(getByText("Sign up with UP mail")).toBeTruthy();
  });

  test("checks login status on component mount", async () => {
    AsyncStorage.getItem.mockResolvedValueOnce("test-token");

    render(<Login />);

    await waitFor(() => {
      expect(AsyncStorage.getItem).toHaveBeenCalledWith("userToken");
      expect(router.push).toHaveBeenCalledWith("/dashboard");
    });
  });

  test("shows a toast if username and password are not provided", async () => {
    const { getByText } = render(<Login />);

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Fill out username and password field",
        visibilityTime: 2000,
        autoHide: true,
      });
    });
  });

  test("shows a toast if only username is provided", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(getByPlaceholderText("Username"), "usernameOnly");

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Fill out password field",
        visibilityTime: 2000,
        autoHide: true,
      });
    });
  });

  test("shows a toast if only password is provided", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(getByPlaceholderText("Password"), "passwordOnly");

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Fill out username field",
        visibilityTime: 2000,
        autoHide: true,
      });
    });
  });

  test("handles successful sign in with correct credentials", async () => {
    const mockResponse = {
      user: {
        uid: "test-uid",
      },
    };

    signInWithEmailAndPassword.mockResolvedValueOnce(mockResponse);

    const { getByText, getByPlaceholderText } = render(<Login />);

    fireEvent.changeText(
      getByPlaceholderText("Username"),
      "kfabriol1@up.edu.ph"
    );
    fireEvent.changeText(getByPlaceholderText("Password"), "upddormtracker");
    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(signInWithEmailAndPassword).toHaveBeenCalledWith(
        FIREBASE_AUTH,
        "kfabriol1@up.edu.ph",
        "upddormtracker"
      );
      expect(AsyncStorage.setItem).toHaveBeenCalledWith(
        "userToken",
        "test-uid"
      );
      expect(AsyncStorage.setItem).toHaveBeenCalledWith(
        "userEmail",
        "kfabriol1@up.edu.ph"
      );
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Successful Log In",
        visibilityTime: 2000,
        autoHide: true,
      });
      expect(router.push).toHaveBeenCalledWith("/dashboard");
    });
  });

  test("handles sign in error", async () => {
    const mockError = new Error("Invalid credentials");
    signInWithEmailAndPassword.mockRejectedValueOnce(mockError);

    const { getByText, getByPlaceholderText } = render(<Login />);

    fireEvent.changeText(
      getByPlaceholderText("Username"),
      "incorrect@up.edu.ph"
    );
    fireEvent.changeText(getByPlaceholderText("Password"), "wrong-password");
    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Alert.alert).toHaveBeenCalledWith("Error " + mockError);
    });
  });

  test("validates email domain before sign up", async () => {
    const { getByText, getByPlaceholderText } = render(<Login />);

    fireEvent.changeText(getByPlaceholderText("Username"), "test@gmail.com");
    fireEvent.changeText(getByPlaceholderText("Password"), "password123");
    fireEvent.press(getByText("Sign up with UP mail"));

    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Only @up.edu.ph emails are allowed for sign up.",
        visibilityTime: 2000,
        autoHide: true,
      });
      expect(createUserWithEmailAndPassword).not.toHaveBeenCalled();
    });
  });

  test("handles successful sign up with UP email", async () => {
    const mockResponse = { user: { email: "new.user@up.edu.ph" } };
    createUserWithEmailAndPassword.mockResolvedValueOnce(mockResponse);

    const { getByText, getByPlaceholderText } = render(<Login />);

    fireEvent.changeText(
      getByPlaceholderText("Username"),
      "new.user@up.edu.ph"
    );
    fireEvent.changeText(getByPlaceholderText("Password"), "secure-password");
    fireEvent.press(getByText("Sign up with UP mail"));

    await waitFor(() => {
      expect(createUserWithEmailAndPassword).toHaveBeenCalledWith(
        FIREBASE_AUTH,
        "new.user@up.edu.ph",
        "secure-password"
      );
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Check your email",
        visibilityTime: 2000,
        autoHide: true,
      });
    });
  });

  test("handles sign up error", async () => {
    const mockError = new Error("Email already in use");
    createUserWithEmailAndPassword.mockRejectedValueOnce(mockError);

    const { getByText, getByPlaceholderText } = render(<Login />);

    fireEvent.changeText(
      getByPlaceholderText("Username"),
      "existing@up.edu.ph"
    );
    fireEvent.changeText(getByPlaceholderText("Password"), "some-password");
    fireEvent.press(getByText("Sign up with UP mail"));

    await waitFor(() => {
      expect(Alert.alert).toHaveBeenCalledWith("Error " + mockError);
    });
  });

  // Legacy tests from existing test suite
  test("shows a toast if incorrect credentials are provided", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(
      getByPlaceholderText("Username"),
      "incorrectCredentials"
    );
    fireEvent.changeText(getByPlaceholderText("Password"), "12345678");

    // Mock Firebase rejection
    const mockError = new Error("Incorrect credentials");
    signInWithEmailAndPassword.mockRejectedValueOnce(mockError);

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Alert.alert).toHaveBeenCalledWith("Error " + mockError);
    });
  });

  test("shows a toast if there is a database connection error", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(getByPlaceholderText("Username"), "databaseError");
    fireEvent.changeText(getByPlaceholderText("Password"), "12345678");

    // Mock Firebase rejection with database error
    const mockError = new Error("No database connection");
    signInWithEmailAndPassword.mockRejectedValueOnce(mockError);

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Alert.alert).toHaveBeenCalledWith("Error " + mockError);
    });
  });
});
