# Import lớp writer từ module csv
from csv import writer
import sys

# Lấy product_id từ đối số dòng lệnh
product_id = str(sys.argv[1])

# Mở tệp CSV hiện có trong chế độ append (thêm vào cuối)
# Tạo một đối tượng tệp cho tệp này
with open('E:\\CayvaCaCanh\\src\\main\\resources\\python\\Py-csv\\products.csv', 'a', newline='') as f_object:

    # Truyền đối tượng tệp này cho csv.writer()
       # và nhận một đối tượng writer
    writer_object = writer(f_object)

    # Write the product_id as a new row
    writer_object.writerow([product_id])
    # Close the file object
    f_object.close()

print([product_id])
