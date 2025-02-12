import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react-native";
import { Login } from "./login";
import { useRouter as useRouterOriginal } from "expo-router";
import Toast from "react-native-toast-message";

// Mock the router
jest.mock("expo-router", () => ({
  useRouter: jest.fn(),
}));

// Mock Toast
jest.mock("react-native-toast-message", () => ({
  show: jest.fn(),
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
  });

  afterEach(() => {
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

  test("shows a toast if incorrect credentials are provided", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(
      getByPlaceholderText("Username"),
      "incorrectCredentials"
    );
    fireEvent.changeText(getByPlaceholderText("Password"), "12345678");

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "Incorrect username or password",
        visibilityTime: 2000,
        autoHide: true,
      });
    });
  });

  test("shows a toast if there is a database connection error", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(getByPlaceholderText("Username"), "databaseError");
    fireEvent.changeText(getByPlaceholderText("Password"), "12345678");

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(Toast.show).toHaveBeenCalledWith({
        type: "error",
        text1: "No database connection",
        visibilityTime: 2000,
        autoHide: true,
      });
    });
  });

  test("navigates to dashboard when correct credentials are provided", async () => {
    const { getByPlaceholderText, getByText } = render(<Login />);

    fireEvent.changeText(getByPlaceholderText("Username"), "correctUsername");
    fireEvent.changeText(getByPlaceholderText("Password"), "correctPassword");

    fireEvent.press(getByText("Sign in"));

    await waitFor(() => {
      expect(router.push).toHaveBeenCalledWith("/dashboard");
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
});
