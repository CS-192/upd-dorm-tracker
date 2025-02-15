// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import {getAuth} from "firebase/auth";
import {getFirestore} from 'firebase/firestore';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCXidSH6Ie5ANKWL_SO3tbeXa1-uF8qDqY",
  authDomain: "upd-dorm-tracker-a345b.firebaseapp.com",
  projectId: "upd-dorm-tracker-a345b",
  storageBucket: "upd-dorm-tracker-a345b.firebasestorage.app",
  messagingSenderId: "1088718055942",
  appId: "1:1088718055942:web:215f1d974de9f77c56b187"
};

// Initialize Firebase
export const FIREBASE_APP = initializeApp(firebaseConfig);
export const FIREBASE_AUTH = getAuth(FIREBASE_APP);
export const FIREBASE_DB= getFirestore(FIREBASE_APP);