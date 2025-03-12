// __mocks__/firebase.js
export const FIREBASE_DB = {};

export const collection = jest.fn();
export const addDoc = jest.fn(() => Promise.resolve());