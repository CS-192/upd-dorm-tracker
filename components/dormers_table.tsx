import { ScrollView, View } from "react-native";
import styles from "@/app/styles";
import { Table, Row, Rows, Cell, TableWrapper } from "react-native-reanimated-table";

const DormersTableComponent = () => {
  const tableHead = ["Room No.", "Name", "Action"];
  const tableData = [
    ["E138A", "Chou, Tzuyu", "View"],
    ["E138B", "Park, Chaewon", "View"],
    ["E138C", "Son, Wendy", "View"],
    ["E138D", "Kim, Gaeul", "View"],
    ["E137A", "House, Gregory", "View"],
    ["E137B", "Chase, Robert", "View"],
    ["E137C", "Cameron, Alison", "View"],
    ["E137D", "Foreman, Eric", "View"],
    ["E136A", "Odegaard, Martin", "View"],
    ["E136B", "Haaland, Erling", "View"],
    ["E136C", "Havertz, Kai", "View"],
    ["E136D", "Tomiyasu, Takehiro", "View"],
    ["E135A", "Park, Rose", "View"],
    ["E135B", "Choi, Lia", "View"],
    ["E135C", "Miyawaki, Sakura", "View"],
    ["E135D", "Fukutomi, Tsuki", "View"],
    ["E134A", "Gillard, Julia", "View"],
    ["E134B", "Shimamura, Haruko", "View"],
    ["E134C", "Adams, Henry John", "View"],
    ["E134D", "Hougan, Lorerans", "View"]
]

  return (
    
    <View style={styles.tableContainer}>
        <Table>
            <Row data={tableHead} style={styles.head} textStyle={styles.headtext} />
        </Table>
        <ScrollView style={styles.tableScrollView}>
            <Table>
                <Rows data={tableData} style={styles.row} textStyle={styles.text} />
            </Table>
        </ScrollView>
        
    </View>
    
  );
};

export default DormersTableComponent;


