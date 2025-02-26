import { ScrollView, View } from "react-native";
import styles from "@/app/styles";
import { Table, Row, Rows, Cell, TableWrapper } from "react-native-table-component";

const TableComponent = () => {
  const tableHead = ["Date", "Student No.", "Actions", ""];
  const tableData = [
    ["1/15/2024", "202300765", "Details/Edit", "Delete"],
    ["2/10/2024", "202300812", "Details/Edit", "Delete"],
    ["3/5/2024", "202300934", "Details/Edit", "Delete"],
    ["4/1/2024", "202301056", "Details/Edit", "Delete"],
    ["5/20/2024", "202301178", "Details/Edit", "Delete"],
    ["6/18/2024", "202301290", "Details/Edit", "Delete"],
    ["7/25/2024", "202301345", "Details/Edit", "Delete"],
    ["8/12/2024", "202301467", "Details/Edit", "Delete"],
    ["9/7/2024", "202301589", "Details/Edit", "Delete"],
    ["10/3/2024", "202301623", "Details/Edit", "Delete"],
    ["11/15/2024", "202301756", "Details/Edit", "Delete"],
    ["12/21/2024", "202301878", "Details/Edit", "Delete"],
    ["1/10/2023", "202200562", "Details/Edit", "Delete"],
    ["2/5/2023", "202200614", "Details/Edit", "Delete"],
    ["3/18/2023", "202200726", "Details/Edit", "Delete"],
    ["4/30/2023", "202200838", "Details/Edit", "Delete"],
    ["5/25/2023", "202200950", "Details/Edit", "Delete"],
    ["6/20/2023", "202201062", "Details/Edit", "Delete"],
    ["7/15/2023", "202201174", "Details/Edit", "Delete"],
    ["8/8/2023", "202201286", "Details/Edit", "Delete"]
]

  return (
    
    <View style={styles.tableContainer}>
        <Table borderStyle={{ borderTopWidth: 1, borderColor: "#ccc" }}>
            <Row data={tableHead} style={styles.head} textStyle={styles.headtext} />
        </Table>
        <ScrollView style={styles.tableScrollView}
            contentContainerStyle={styles.tableScrollContent}>
            <Table borderStyle={ {borderTopWidth: 1, borderColor: "#ccc" }}>
                <Rows data={tableData} style={styles.row} textStyle={styles.text} />
            </Table>
        </ScrollView>
        
    </View>
    
  );
};

export default TableComponent;

