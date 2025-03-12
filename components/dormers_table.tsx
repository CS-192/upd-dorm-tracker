import {
  ScrollView,
  View,
  TouchableOpacity,
  Text,
  Dimensions,
} from "react-native";
import { useEffect, useState } from "react";
import { collection, getDocs, query, where } from "firebase/firestore";
import { FIREBASE_DB } from "@/FirebaseConfig";
import styles from "@/app/styles";
import { Table, Row, Rows, Cell } from "react-native-reanimated-table";
import { Link } from "expo-router";

const DormersTableComponent = () => {
  const [tableData, setTableData] = useState<string[][]>([]);
  const tableHead = ["Room No.", "Name", "Action"];
  const screenWidth = Dimensions.get("window").width;
  const columnWidths = [
    screenWidth * 0.2,
    screenWidth * 0.49,
    screenWidth * 0.15,
  ];

  const dormQueried = "Molave";

  useEffect(() => {
    const fetchDormers = async () => {
      try {
        const dormersRef = collection(FIREBASE_DB, "dormers");
        const molaveQuery = query(dormersRef, where("dorm", "==", dormQueried)); // 🔥 Filter by dorm
        const querySnapshot = await getDocs(molaveQuery);

        const dormersArray: string[][] = querySnapshot.docs.map((doc) => {
          const data = doc.data();
          return [
            data.room_number || "N/A", // Room Number
            `${data.last_name}, ${data.first_name}`, // Name (Last, First)
            data.id, // Store the document ID to use in the link
          ];
        });

        setTableData(dormersArray);
      } catch (error) {
        console.error("Error fetching dormers:", error);
      }
    };

    fetchDormers();
  }, []);

  // Render clickable "View" link
  const renderViewButton = (docId: string) => (
    <Link href={`/`} asChild>
      <TouchableOpacity>
        <Text>View</Text>
      </TouchableOpacity>
    </Link>
  );

  return (
    <View style={styles.tableContainer}>
      <Table>
        <Row
          data={tableHead}
          style={styles.head}
          textStyle={styles.headtext}
          widthArr={columnWidths}
        />
      </Table>
      <ScrollView style={styles.tableScrollView}>
        <Table>
          {tableData
            .sort((a: string[], b: string[]) =>
              a[0].localeCompare(b[0], undefined, { numeric: true })
            )
            .map((row, index) => (
              <Row
                key={index}
                data={[
                  row[0], // Room No.
                  row[1], // Name
                  renderViewButton(row[2]), // Clickable "View" button
                ]}
                style={styles.row}
                textStyle={styles.text}
                widthArr={columnWidths}
              />
            ))}
        </Table>
      </ScrollView>
    </View>
  );
};

export default DormersTableComponent;
