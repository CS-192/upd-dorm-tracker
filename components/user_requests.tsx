import { ScrollView, View } from "react-native";
import styles from "@/app/styles";
import { StyleSheet } from 'react-native';
import { Table, Row, Rows } from "react-native-reanimated-table";

const UserRequests = () => {
  const tableHead = ["Date", "Request Type", "Status"];
  const tableData = [
    ["29/10/2024", "Monthly Bill", "Pending"],
    ["29/10/2024", "Late Night Pass", "Accepted"],
    ["29/10/2024", "Report", "Rejected"],
    ["29/10/2024", "Monthly Bill", "Pending"],
    ["29/10/2024", "Late Night Pass", "Accepted"],
    ["29/10/2024", "Report", "Rejected"],
    ["29/10/2024", "Monthly Bill", "Pending"],
    ["29/10/2024", "Late Night Pass", "Accepted"],
    ["29/10/2024", "Report", "Rejected"],
    ["29/10/2024", "Monthly Bill", "Pending"],
    ["29/10/2024", "Late Night Pass", "Accepted"],
    ["29/10/2024", "Report", "Rejected"],
    ["29/10/2024", "Monthly Bill", "Pending"],
    ["29/10/2024", "Late Night Pass", "Accepted"],
    ["29/10/2024", "Report", "Rejected"],
    ["29/10/2024", "Monthly Bill", "Pending"],
    ["29/10/2024", "Late Night Pass", "Accepted"],
    ["29/10/2024", "Report", "Rejected"],
]

    const statusColor = (status: string) => {
        switch (status) {
            case "Pending":
                return "#AA6000";
            case "Accepted":
                return "#009A05"; 
            case "Accepted":
                return "#FF0404";
            default:
                return "#000000"
        }
    }

  return (
    
    <View style={styles.tableContainer}>
        <Table>
            <Row data={tableHead} style={styles.head} textStyle={styles.headtext} flexArr={[1.5, 1.5, 1, 1]}/>
        </Table>
        <ScrollView style={styles.tableScrollView}>
            <Table>
                <Rows data={tableData} style={styles.row} textStyle={styles.text} flexArr={[1.5, 1.5, 1, 1]} />
            </Table>
        </ScrollView>
        
    </View>
    
  );
};

export default UserRequests;

