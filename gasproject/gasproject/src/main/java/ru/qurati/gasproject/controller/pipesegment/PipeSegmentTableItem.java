package ru.qurati.gasproject.controller.pipesegment;

import javafx.beans.property.SimpleStringProperty;
import ru.qurati.gasproject.model.PipeSegment;
import java.time.format.DateTimeFormatter;

public class PipeSegmentTableItem {
    private SimpleStringProperty className;
    private SimpleStringProperty segmentNumber;
    private SimpleStringProperty status;
    private SimpleStringProperty lastInspectionDate;
    private SimpleStringProperty lengthKm;
    private PipeSegment segment;

    public PipeSegmentTableItem(PipeSegment segment, String className) {
        this.className = new SimpleStringProperty(className);
        this.segmentNumber = new SimpleStringProperty(segment.getSegmentNumber());
        this.status = new SimpleStringProperty(segment.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.lastInspectionDate = new SimpleStringProperty(
                segment.getLastInspectionDate() != null ? segment.getLastInspectionDate().format(formatter) : ""
        );
        this.lengthKm = new SimpleStringProperty(segment.getLengthKm() != null ? segment.getLengthKm().toString() : "");
        this.segment = segment;
    }

    public String getClassName() { return className.get(); }
    public SimpleStringProperty classNameProperty() { return className; }

    public String getSegmentNumber() { return segmentNumber.get(); }
    public SimpleStringProperty segmentNumberProperty() { return segmentNumber; }

    public String getStatus() { return status.get(); }
    public SimpleStringProperty statusProperty() { return status; }

    public String getLastInspectionDate() { return lastInspectionDate.get(); }
    public SimpleStringProperty lastInspectionDateProperty() { return lastInspectionDate; }

    public String getLengthKm() { return lengthKm.get(); }
    public SimpleStringProperty lengthKmProperty() { return lengthKm; }

    public PipeSegment getSegment() { return segment; }
}