import { initializeApp } from "firebase/app";
import { initializeAuth, getReactNativePersistence } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import ReactNativeAsyncStorage from "@react-native-async-storage/async-storage";

const firebaseConfig = {
  apiKey: "AIzaSyCXidSH6Ie5ANKWL_SO3tbeXa1-uF8qDqY",
  authDomain: "upd-dorm-tracker-a345b.firebaseapp.com",
  projectId: "upd-dorm-tracker-a345b",
  storageBucket: "upd-dorm-tracker-a345b.firebasestorage.app",
  messagingSenderId: "1088718055942",
  appId: "1:1088718055942:web:215f1d974de9f77c56b187",
};

export const FIREBASE_APP = initializeApp(firebaseConfig);
export const FIREBASE_AUTH = initializeAuth(FIREBASE_APP, {
  persistence: getReactNativePersistence(ReactNativeAsyncStorage),
});
export const FIREBASE_DB = getFirestore(FIREBASE_APP);
